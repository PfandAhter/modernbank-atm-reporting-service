package com.modernbank.atm_reporting_service.repository;

import com.modernbank.atm_reporting_service.model.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

}