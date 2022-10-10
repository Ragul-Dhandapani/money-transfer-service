package com.org.moneytransfers.exceptions;

import com.org.moneytransfers.dto.response.TransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import static com.org.moneytransfers.constants.ApplicationConstants.FAILED;

@ControllerAdvice
@ResponseBody
public class ExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler({TransactionException.class, InterruptedException.class,
            ExecutionException.class, JpaSystemException.class})
    private ResponseEntity<TransactionResponse> genericException(Exception exception) {
        LOGGER.error(exception.getMessage());

        return new ResponseEntity<>(TransactionResponse.builder().status(FAILED)
                .message(exception.getMessage())
                .localDateTime(LocalDateTime.now()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({CustomerAccountNotFoundException.class,
            IllegalArgumentException.class, MethodArgumentNotValidException.class})
    private ResponseEntity<TransactionResponse> accountNotFoundException(Exception exception) {
        LOGGER.error(exception.getMessage());
        return new ResponseEntity<>(TransactionResponse.builder().status(FAILED)
                .message(exception.getMessage())
                .localDateTime(LocalDateTime.now()).build(), HttpStatus.NOT_ACCEPTABLE);
    }
}
