package com.modernbank.atm_reporting_service.model.enums;

public enum ATMDepositStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    TOO_MUCH_MONEY("TOO MUCH MONEY"),
    NETWORK_ERROR("NETWORK ERROR"),
    ATM_OUT_OF_SERVICE("ATM OUT OF SERVICE"),
    EXCEEDS_DAILY_LIMIT("EXCEEDS DAILY LIMIT");

    private final String status;

    ATMDepositStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
