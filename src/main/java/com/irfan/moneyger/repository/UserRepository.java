package com.irfan.moneyger.repository;

import com.irfan.moneyger.entity.MUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<MUser, String>, JpaSpecificationExecutor<MUser> {
    @Modifying
    @Query(value = "INSERT INTO m_user (id, first_name, last_name, balance, is_active) VALUES(:id, :firstName, :lastName, :balance, :isActive)", nativeQuery = true)
    void createQuery(
        @Param("id") String id,
        @Param("firstName") String firstName,
        @Param("lastName") String lastName,
        @Param("balance") Long balance,
        @Param("isActive") Boolean isActive
    );

    @Modifying
    @Query(value = "UPDATE m_user SET first_name = :firstName, last_name = :lastName, balance = :balance WHERE id = :id", nativeQuery = true)
    void updateQuery(
        @Param("id") String id,
        @Param("firstName") String firstName,
        @Param("lastName") String lastName,
        @Param("balance") Long balance
    );

    @Modifying
    @Query(value = "UPDATE m_user SET is_active = :isActive WHERE id = :id", nativeQuery = true)
    void updateIsActiveQuery(
        @Param("id") String id,
        @Param("isActive") Boolean isActive
    );

    @Modifying
    @Query(value = "UPDATE m_user SET balance = :balance WHERE id = :id", nativeQuery = true)
    void updateBalanceQuery(
        @Param("id") String id,
        @Param("balance") Long balance
    );
}
