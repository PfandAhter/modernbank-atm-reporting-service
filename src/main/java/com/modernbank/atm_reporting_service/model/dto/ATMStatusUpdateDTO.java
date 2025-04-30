package com.modernbank.atm_reporting_service.model.dto;

import com.modernbank.atm_reporting_service.model.entity.Bank;
import com.modernbank.atm_reporting_service.model.enums.ATMDepositStatus;
import com.modernbank.atm_reporting_service.model.enums.ATMStatus;
import com.modernbank.atm_reporting_service.model.enums.ATMWithdrawStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ATMStatusUpdateDTO {

    private String name;

    private String latitude;

    private String longitude;

    private String district;

    private String address;

    private ATMStatus status;

    private ATMDepositStatus depositStatus;

    private ATMWithdrawStatus withdrawStatus;

    private Set<Bank> supportedBanks = new HashSet<>();
}