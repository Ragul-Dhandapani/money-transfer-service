package com.org.moneytransfers.exceptions;

public class TransactionException extends RuntimeException {

    public TransactionException() {
        super();
    }

    public TransactionException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public TransactionException(Throwable throwable) {
        super(throwable);
    }

    public TransactionException(String exceptionMessage , Throwable throwable) {
        super(exceptionMessage , throwable);
    }
}
