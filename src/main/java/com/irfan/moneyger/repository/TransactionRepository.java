package com.irfan.moneyger.repository;

import com.irfan.moneyger.entity.TTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface TransactionRepository extends JpaRepository<TTransaction, String>, JpaSpecificationExecutor<TTransaction> {
    @Modifying
    @Query(value = "INSERT INTO t_transaction (id, user_id, date, category, income, expense, balance) VALUES (:id, :userId, :date, :category, :income, :expense, :balance)", nativeQuery = true)
    void createQuery(
            String id,
            String userId,
            Date date,
            String category,
            Long income,
            Long expense,
            Long balance
    );

    @Modifying
    @Query(value = "UPDATE t_transaction SET user_id = :userId, date = :date, category = :category, income = :income, expense = :expense, balance = :balance WHERE id = :id", nativeQuery = true)
    void updateQuery(
            String id,
            String userId,
            Date date,
            String category,
            Long income,
            Long expense,
            Long balance
    );
}
