package com.modernbank.atm_reporting_service.model.enums;

public enum ATMWithdrawStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    INSUFFICIENT_FUNDS("INSUFFICIENT FUNDS"),
    ATM_OUT_OF_SERVICE("ATM OUT OF SERVICE"),
    EXCEEDS_DAILY_LIMIT("EXCEEDS DAILY LIMIT"),
    NETWORK_ERROR("NETWORK ERROR");

    private final String status;

    ATMWithdrawStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
