package com.org.moneytransfers.service;

import com.org.moneytransfers.constants.ApplicationConstants;
import com.org.moneytransfers.dto.MoneyTransferInfoDto;
import com.org.moneytransfers.dto.response.TransactionResponse;
import com.org.moneytransfers.entities.AccountBalanceEntity;
import com.org.moneytransfers.entities.AccountEntity;
import com.org.moneytransfers.exceptions.CustomerAccountNotFoundException;
import com.org.moneytransfers.exceptions.TransactionException;
import com.org.moneytransfers.repository.AccountBalanceRepository;
import com.org.moneytransfers.repository.CustomerAccountRepository;
import com.org.moneytransfers.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.org.moneytransfers.constants.ApplicationConstants.ACCOUNT_WAIT_INTERVAL;
import static com.org.moneytransfers.constants.ApplicationConstants.SUCCESS;

@Service
public class TransactionService {

    @Autowired
    private CustomerAccountRepository accountRepository;
    @Autowired
    private AccountBalanceRepository accountBalanceRepository;


    @Transactional(rollbackOn = Exception.class)
    public TransactionResponse sendMoney(MoneyTransferInfoDto moneyTransferInfo) {
        final Lock debitLock = new ReentrantLock();
        TransactionResponse transactionResponse;
        try {
            transactionResponse =  CompletableFuture.supplyAsync(() -> {
                try {
                    if (debitLock.tryLock(ACCOUNT_WAIT_INTERVAL , TimeUnit.MILLISECONDS)) {
                        AccountEntity sourceAccountInfo = accountRepository.findById(moneyTransferInfo.getSourceAccountId()).get();
                        Validator.validateCurrencyIsTheSame(sourceAccountInfo.getAccountBalanceEntity().getBaseCurrency() , moneyTransferInfo.getDestinationCurrency());
                        Validator.validateGivenBalanceIsGreater(sourceAccountInfo.getAccountBalanceEntity().getBalance() , moneyTransferInfo.getAmountToBeTransferred());
                        final Lock creditLock = new ReentrantLock();
                        try {
                            if (creditLock.tryLock(ACCOUNT_WAIT_INTERVAL , TimeUnit.MILLISECONDS)) {
                                AccountBalanceEntity targetAccountInformation = accountBalanceRepository.findById(moneyTransferInfo.getTargetAccountId())
                                        .orElseThrow(() -> new CustomerAccountNotFoundException("Target Account Not found in the System"));

                                targetAccountInformation.setUpdatedOn(LocalDateTime.now(ZoneOffset.UTC));
                                targetAccountInformation.setBalance(moneyTransferInfo.getAmountToBeTransferred().add(sourceAccountInfo.getAccountBalanceEntity().getBalance()));

                                BigDecimal remainingBalance = sourceAccountInfo.getAccountBalanceEntity().getBalance().subtract(moneyTransferInfo.getAmountToBeTransferred());
                                sourceAccountInfo.getAccountBalanceEntity().setBalance(remainingBalance);
                                sourceAccountInfo.getAccountBalanceEntity().setUpdatedOn(LocalDateTime.now(ZoneOffset.UTC));

                                accountRepository.save(sourceAccountInfo);
                                accountBalanceRepository.save(targetAccountInformation);
                            }
                        } catch (Exception e) {
                            throw new TransactionException(ApplicationConstants.TRANSACTION_EXCEPTION, e);
                        } finally {
                            creditLock.unlock();
                        }
                    }
                }catch(TransactionException e) {
                    throw new TransactionException(e);
                } catch (Exception e) {
                    throw new TransactionException(e.getMessage().isEmpty() ? ApplicationConstants.TRANSACTION_EXCEPTION : e.getMessage() , e);
                } finally {
                    debitLock.unlock();
                }

                return true;
            }).thenApply(res -> TransactionResponse.builder().status(SUCCESS)
                    .message(ApplicationConstants.TRANSFER_COMPLETED_SUCCESS_MSG).localDateTime(LocalDateTime.now(ZoneOffset.UTC)).build()).get();
        } catch (Exception exception) {
            throw new TransactionException(ApplicationConstants.TRANSACTION_EXCEPTION , exception);
        }
        return transactionResponse;
    }
}
