package com.org.moneytransfers.repository;

import com.org.moneytransfers.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerAccountRepository extends JpaRepository<AccountEntity, Long> {

    //@Query(value = "SELECT * FROM CUSTOMER_ACCOUNT_INFO ca INNER JOIN ACCOUNT_BALANCE_INFO ab ON ca.account_balance_entity_id=ab.id where ca.account_number= ?1")
    Optional<AccountEntity> findById(Long accountId);

}
