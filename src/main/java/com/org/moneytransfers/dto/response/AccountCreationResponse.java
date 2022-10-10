package com.org.moneytransfers.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Data
@Builder
@ResponseBody
public class AccountCreationResponse {

    private Long accountNumber;
    private Long sortCode;
    private String status;
    private String message;
    private LocalDateTime localDateTime;
}
