package com.modernbank.atm_reporting_service.model.entity;

import com.modernbank.atm_reporting_service.model.enums.ATMDepositStatus;
import com.modernbank.atm_reporting_service.model.enums.ATMStatus;
import com.modernbank.atm_reporting_service.model.enums.ATMWithdrawStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "atm")
@Getter
@Setter

public class ATM {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "address")
    private String address;

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