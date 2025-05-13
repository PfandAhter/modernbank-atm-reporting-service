package com.modernbank.atm_reporting_service.model.enums;

import java.util.Arrays;
import java.util.Optional;

public enum PDFGenerationTypes {
    ATMReportInvoice("ATM_REPORT_INVOICE");

    private final String type;

    PDFGenerationTypes(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public static Optional<PDFGenerationTypes> fromString(String type) {
        return Arrays.stream(values())
                .filter(e -> e.type.equalsIgnoreCase(type))
                .findFirst();
    }
}
