package com.org.moneytransfers.repository;

import com.org.moneytransfers.entities.AccountBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBalanceRepository extends JpaRepository<AccountBalanceEntity,Long> {
}
