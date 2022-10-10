package com.org.moneytransfers.controller;

import com.org.moneytransfers.entities.AccountEntity;
import com.org.moneytransfers.dto.response.AccountCreationResponse;
import com.org.moneytransfers.repository.CustomerAccountRepository;
import com.org.moneytransfers.service.AccountService;
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

@RequestMapping("/account/")
@RestController
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "Account Creation", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "create")
    private ResponseEntity<AccountCreationResponse> createAccount(@RequestBody @Valid AccountEntity accountEntity) {
        return new ResponseEntity<>(accountService.createAccount(accountEntity) , HttpStatus.OK);
    }

}