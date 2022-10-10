package com.org.moneytransfers.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRANSACTION")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SOURCE_ACCOUNT_ID",nullable = false, updatable = false)
    private @NotNull Long sourceAccountId;

    @Column(name = "TARGET_ACCOUNT_ID",nullable = false, updatable = false)
    private @NotNull Long targetAccountId;

    @Column(name = "TARGET_AMOUNT",nullable = false, updatable = false)
    private BigDecimal transferAmount;

    @Column(name = "TARGET_CURRENCY",nullable = false)
    private @NotNull String currency;

    @Column(name = "CREATED_AT")
    private final LocalDateTime createdAt= LocalDateTime.now(ZoneOffset.UTC);

    @Column(name = "UPDATED_ON")
    private final LocalDateTime updatedOn= LocalDateTime.now(ZoneOffset.UTC);

}
