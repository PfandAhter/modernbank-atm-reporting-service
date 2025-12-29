package com.modernbank.atm_reporting_service.websocket.controller;

import com.modernbank.atm_reporting_service.api.PDFGeneratorControllerApi;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreatePDFRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.PDFGeneratorResponse;
import com.modernbank.atm_reporting_service.websocket.service.event.producer.PDFProducerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //TODO : burayi guncelle...
@CrossOrigin
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class PDFGeneratorController implements PDFGeneratorControllerApi {

    private final PDFProducerServiceImpl pdfProducerService;

    @Override
    public ResponseEntity<PDFGeneratorResponse> pdfGenerate(CreatePDFRequest request) {
//        validation();
        return ResponseEntity.ok(pdfProducerService.createATMPDFReport(request));
    }
}