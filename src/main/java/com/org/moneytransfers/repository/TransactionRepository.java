package com.org.moneytransfers.repository;

import com.org.moneytransfers.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {

}
