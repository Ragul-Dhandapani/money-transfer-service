package com.org.moneytransfers.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Random;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CUSTOMER_ACCOUNT_INFO")
@DynamicUpdate
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ACCOUNT_NUMBER", unique = true, nullable = false)
    private final Long accountNumber= generateRandomDigits();

    @NotNull(message = "Account Holder Name cannot be empty / null")
    @JsonProperty(value = "account_holder_name")
    @Column(name = "ACCOUNT_HOLDER_NAME",nullable = false)
    private String accountHolderName;

    @NotNull(message = "Account Holder Email Id cannot be empty /null")
    @JsonProperty(value = "account_holder_email")
    @Column(name = "ACCOUNT_HOLDER_EMAIL",nullable = false, unique = true)
    private String accountHolderEmail;

    @NotNull(message = "Account Sort Code cannot be empty / null")
    @JsonProperty(value = "account_sort_code")
    @Column(name = "ACCOUNT_SORT_CODE",nullable = false)
    private Long accountSortCode;

    @JsonProperty(value = "account_balance_info",required = true)
    @OneToOne(cascade = CascadeType.ALL , orphanRemoval = true)
    private AccountBalanceEntity accountBalanceEntity;

    private static Long generateRandomDigits(){
        return (long) new Random().nextInt(999999);
    }
}
