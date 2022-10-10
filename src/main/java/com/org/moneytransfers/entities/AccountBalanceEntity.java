package com.org.moneytransfers.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACCOUNT_BALANCE_INFO")
@DynamicUpdate
public class AccountBalanceEntity {
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "balance cannot be empty or null")
    @Min(value = 0, message = "amount must be greater than 0")
    @JsonProperty(value = "balance", required = true)
    @Column(name = "BALANCE", nullable = false)
    private BigDecimal balance;

    @JsonProperty(value = "base_currency", required = true)
    @Column(name = "BASE_CURRENCY", nullable = false)
    private String baseCurrency;

    @Column(name = "UPDATED_ON", nullable = false)
    private LocalDateTime updatedOn = LocalDateTime.now(ZoneOffset.UTC);

    @OneToOne(mappedBy = "accountBalanceEntity",fetch = FetchType.LAZY, optional = false)
    private AccountEntity accountEntity;

}
