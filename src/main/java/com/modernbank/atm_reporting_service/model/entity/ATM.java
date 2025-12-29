package com.modernbank.atm_reporting_service.model.entity;

import com.modernbank.atm_reporting_service.model.enums.ATMDepositStatus;
import com.modernbank.atm_reporting_service.model.enums.ATMStatus;
import com.modernbank.atm_reporting_service.model.enums.ATMWithdrawStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;




@Entity
@Table(name = "atm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ATM {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "address")
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "atm_banks",
            joinColumns = @JoinColumn(name = "atm_id"),
            inverseJoinColumns = @JoinColumn(name = "bank_id")
    )
    private Set<Bank> supportedBanks = new HashSet<>();

    @Column(name = "status")
    private ATMStatus status;

    @Column(name = "deposit_status")
    private ATMDepositStatus depositStatus;

    @Column(name = "withdraw_status")
    private ATMWithdrawStatus withdrawStatus;

    @Column(name = "created_date")
    private String createdDate;

    @Column(name = "last_updated_date")
    private String lastUpdatedDate;

}