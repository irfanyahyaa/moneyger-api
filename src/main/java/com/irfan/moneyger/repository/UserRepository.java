package com.irfan.moneyger.repository;

import com.irfan.moneyger.entity.MUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MUser, String> {
}
