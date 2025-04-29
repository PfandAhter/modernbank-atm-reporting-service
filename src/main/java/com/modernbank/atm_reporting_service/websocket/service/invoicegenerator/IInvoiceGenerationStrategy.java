package com.modernbank.atm_reporting_service.websocket.service.invoicegenerator;

import java.util.Map;

public interface IInvoiceGenerationStrategy {
    String generationType();

    byte[] generatePDF(Map<String, Object> data);
}