package com.modernbank.atm_reporting_service.model.enums;

public enum ATMStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    UNDER_MAINTENANCE("UNDER MAINTENANCE"),
    INSUFFICIENT_FUNDS("INSUFFICIENT FUNDS"),
    ATM_OUT_OF_SERVICE("ATM OUT OF SERVICE"),
    NETWORK_ERROR("NETWORK ERROR"),
    OUT_OF_SERVICE("OUT OF SERVICE"),
    UNKNOWN("UNKNOWN");

    private final String status;

    ATMStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}