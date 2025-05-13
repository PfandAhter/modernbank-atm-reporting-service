package com.modernbank.atm_reporting_service.websocket.service.invoicegenerator;

import com.itextpdf.text.Document;

import java.util.Map;

public interface IPDFGenerationStrategy {
    void generatePDF(Document document, Map<String, Object> data);
}