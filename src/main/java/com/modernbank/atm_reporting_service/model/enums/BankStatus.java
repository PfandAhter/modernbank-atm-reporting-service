package com.modernbank.atm_reporting_service.model.enums;

public enum BankStatus {
    ACTIVE,
    INACTIVE,
    PENDING,
    SUSPENDED,
    DELETED;

    public static BankStatus fromString(String status) {
        for (BankStatus bankStatus : BankStatus.values()) {
            if (bankStatus.name().equalsIgnoreCase(status)) {
                return bankStatus;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}
