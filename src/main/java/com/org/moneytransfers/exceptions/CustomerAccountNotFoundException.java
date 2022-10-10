package com.org.moneytransfers.exceptions;

import java.util.function.Supplier;

public class CustomerAccountNotFoundException extends RuntimeException {

    public CustomerAccountNotFoundException() {
        super();
    }

    public CustomerAccountNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public CustomerAccountNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public CustomerAccountNotFoundException(String exceptionMessage , Throwable throwable) {
        super(exceptionMessage , throwable);
    }
}
