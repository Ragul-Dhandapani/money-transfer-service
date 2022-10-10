package com.org.moneytransfers.service;

import com.org.moneytransfers.dto.response.AccountCreationResponse;
import com.org.moneytransfers.entities.AccountEntity;
import com.org.moneytransfers.enums.ErrorEnum;
import com.org.moneytransfers.exceptions.TransactionException;
import com.org.moneytransfers.repository.CustomerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

import static com.org.moneytransfers.constants.ApplicationConstants.ACCOUNT_CREATION_SUCCESS_MSG;
import static com.org.moneytransfers.constants.ApplicationConstants.SUCCESS;

@Service
public class AccountService {

    @Autowired
    private CustomerAccountRepository accountRepository;

    @Transactional(rollbackOn = Exception.class)
    public AccountCreationResponse createAccount(AccountEntity accountEntity) {
        AccountCreationResponse accountCreationResponse = null;
        
        try {
            accountCreationResponse = CompletableFuture.supplyAsync(() -> accountRepository.save(accountEntity))
                    .thenApply(res -> AccountCreationResponse.builder()
                            .accountNumber(res.getAccountNumber())
                            .sortCode(res.getAccountSortCode())
                            .localDateTime(accountEntity.getAccountBalanceEntity().getCreatedAt())
                            .status(SUCCESS)
                            .message(ACCOUNT_CREATION_SUCCESS_MSG)
                            .build()).get();

        } catch (Exception exception) {
            throw new TransactionException(String.valueOf(ErrorEnum.ACCOUNT_CREATION_FAILED));
        }
        return accountCreationResponse;
    }
}
