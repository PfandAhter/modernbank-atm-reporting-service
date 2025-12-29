package com.modernbank.atm_reporting_service.repository;

import com.modernbank.atm_reporting_service.model.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    @Query("SELECT b FROM Bank  b WHERE (?1 = 'all') or b.name = ?1")
    Optional<Bank> findBankByName(String name);

    @Query("SELECT b FROM Bank b WHERE (:name = 'all') OR b.name = :name")
    List<Bank> findBanksByAll(@Param("name") String name);
}