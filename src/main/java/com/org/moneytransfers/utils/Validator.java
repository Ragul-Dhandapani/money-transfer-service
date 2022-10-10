package com.org.moneytransfers.utils;

import com.org.moneytransfers.exceptions.TransactionException;

import java.math.BigDecimal;

public class Validator {

    public static void validateAmountPositive(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionException("Amount must be greater than zero");
        }
    }

    public static void validateCurrencyIsTheSame(String sourceCurrency,String targetCurrency) {
        if (!sourceCurrency.equalsIgnoreCase(targetCurrency)) {
            throw new TransactionException(
                    String.format("Multi-currency operations are not supported. Debit account = %s; credit account = %s", sourceCurrency, targetCurrency));
        }
    }


    public static void validateGivenBalanceIsGreater(BigDecimal sourceAccountBalance , BigDecimal amountToBeTransferred) {
        if(sourceAccountBalance.compareTo(amountToBeTransferred) < 0)
            throw new TransactionException("Insufficient Funds");
    }
}
