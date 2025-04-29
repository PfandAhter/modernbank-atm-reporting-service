package com.modernbank.atm_reporting_service.model.enums;

public enum InvoiceGenerationTypes {
    ATMReportInvoice("ATM_REPORT_INVOICE");

    private final String type;

    InvoiceGenerationTypes(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
