package com.modernbank.atm_reporting_service.repository;

import com.modernbank.atm_reporting_service.model.entity.ATM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ATMRepository extends JpaRepository<ATM,String> {

    @Query("SELECT a FROM ATM a WHERE a.id = ?1")
    Optional<ATM> getATMByATMId(String atmId);

    @Query("SELECT a FROM ATM a WHERE (?1 = 'all') or a.id = ?1")
    Optional<List<ATM>> getATMsByLocation(String atmId);

}