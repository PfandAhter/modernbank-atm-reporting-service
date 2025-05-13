package com.modernbank.atm_reporting_service.websocket.controller.api;

import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreatePDFRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.PDFGeneratorResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IPDFGeneratorControllerApi {

    @PostMapping(path = "/generate-pdf", produces = "application/pdf", consumes = "application/json")
    ResponseEntity<PDFGeneratorResponse> pdfGenerate(@Valid @RequestBody CreatePDFRequest request);
}
