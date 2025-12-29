package com.modernbank.atm_reporting_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "encrypted_pdf")
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EncryptedPDF {

    @Id
    @Column(name = "requestId")
    private String requestId;

    @Lob
    @Column(name = "pdf", columnDefinition = "LONGBLOB")
    private byte[] pdf;

    @Column(name = "salt")
    private String salt;

}