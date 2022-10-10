package com.org.moneytransfers.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {

    private String status;
    private String message;
    private LocalDateTime localDateTime;
}
