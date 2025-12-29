package com.modernbank.atm_reporting_service.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.modernbank.atm_reporting_service.model.enums.BankStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "banks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    private BankStatus status;

    @JsonIgnore
    @ManyToMany(mappedBy = "supportedBanks")
    private Set<ATM> atms = new HashSet<>();

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "last_update_date")
    private String lastUpdateDate;

    @Column(name = "createdBy")
    private String createdById;

    @Column(name = "updatedBy")
    private String updatedById;
}