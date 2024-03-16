package com.irfan.moneyger.repository;

import com.irfan.moneyger.entity.TTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TTransaction, String> {
}
