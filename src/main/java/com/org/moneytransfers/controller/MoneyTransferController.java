package com.org.moneytransfers.controller;

import com.org.moneytransfers.dto.MoneyTransferInfoDto;
import com.org.moneytransfers.dto.response.TransactionResponse;
import com.org.moneytransfers.exceptions.CustomerAccountNotFoundException;
import com.org.moneytransfers.repository.CustomerAccountRepository;
import com.org.moneytransfers.service.TransactionService;
import com.org.moneytransfers.utils.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/transfer/")
@RestController
@Api(value = "Money Transfer Controller")
public class MoneyTransferController {

    private final Logger LOGGER = LoggerFactory.getLogger(MoneyTransferController.class);

    @Autowired
    private CustomerAccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @ApiOperation(value = "Transfer money between two accounts",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "money", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> initiateMoneyTransfers(@RequestBody @Valid MoneyTransferInfoDto moneyTransferInfo) {
        TransactionResponse transactionResponse;
        if (accountRepository.existsById(moneyTransferInfo.getSourceAccountId()) &&
                accountRepository.existsById(moneyTransferInfo.getTargetAccountId())) {
            Validator.validateAmountPositive(moneyTransferInfo.getAmountToBeTransferred());
            transactionResponse = transactionService.sendMoney(moneyTransferInfo);
        } else
            throw new CustomerAccountNotFoundException("Given Account not exists in the System, please verify once again");

        return new ResponseEntity<>(transactionResponse , HttpStatus.OK);
    }
}
