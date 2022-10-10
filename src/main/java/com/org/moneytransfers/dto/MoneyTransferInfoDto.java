package com.org.moneytransfers.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransferInfoDto {

    @NotNull(message = "Source Account number cannot be empty/null ")
    @JsonProperty(value = "source_account_id",required = true)
    private Long sourceAccountId;

    @NotNull(message = "Target Account number cannot be empty/null ")
    @JsonProperty(value = "target_account_id",required = true)
    private Long targetAccountId;

    @NotNull(message = "amount cannot be null")
    @Min(value = 0, message = "amount must be greater than 0")
    @JsonProperty(value = "amount_to_be_transferred",required = true)
    private BigDecimal amountToBeTransferred;

    @NotNull(message = "Destination Currency Cannot be Empty")
    @JsonProperty(value = "destination_currency",required = true)
    private String destinationCurrency;

}
