package com.modernbank.atm_reporting_service.websocket.service.invoicegenerator;

import com.modernbank.atm_reporting_service.model.enums.InvoiceGenerationTypes;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ATMReportInvoiceGenerator implements IInvoiceGenerationStrategy{

    @Override
    public String generationType() {
        return InvoiceGenerationTypes.ATMReportInvoice.getType();
    }

    @Override
    public byte[] generatePDF(Map<String, Object> data) {
        return new byte[0];
    }
}