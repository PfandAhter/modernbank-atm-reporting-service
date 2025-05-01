package com.modernbank.atm_reporting_service.repository;

import com.modernbank.atm_reporting_service.model.entity.EncryptedPDF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EncryptedPDFRepository extends JpaRepository<EncryptedPDF,String> {

    @Query("SELECT pdf FROM EncryptedPDF pdf WHERE pdf.requestId = ?1")
    Optional<EncryptedPDF> findByRequestId(String requestId);

}