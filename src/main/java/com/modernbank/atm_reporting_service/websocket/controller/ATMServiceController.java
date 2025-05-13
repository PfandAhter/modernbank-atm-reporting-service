package com.modernbank.atm_reporting_service.websocket.controller;

import com.modernbank.atm_reporting_service.websocket.controller.api.IATMControllerApi;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreateATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.UpdateATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.*;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IATMService;
import com.modernbank.atm_reporting_service.websocket.service.invoicegenerator.PDFGenerationFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/atm")
//@RequiredArgsConstructor

public class ATMServiceController implements IATMControllerApi {

    private final IATMService atmService;

    private final PDFGenerationFactory generationFactory;

    public ATMServiceController(IATMService atmService, PDFGenerationFactory factory) {
        this.atmService = atmService;
        this.generationFactory = factory;
    }

    @Override
    public ResponseEntity<ATMStatusUpdateResponse> getATMStatusDetails(String atmId) {
        return ResponseEntity.ok(atmService.getATMStatusDetail(atmId));
    }

    @Override
    public ResponseEntity<BaseResponse> createATM(CreateATMRequest request) {
        return ResponseEntity.ok(atmService.createATM(request));
    }

    @Override
    public ResponseEntity<BaseResponse> updateATM(UpdateATMRequest request) {
        return ResponseEntity.ok(atmService.updateATM(request));
    }

    @Override // /api/v1/atm/get {requestParam id = atmid}
    public ResponseEntity<GetAllATMResponse> getAllATMs(String atmId) {
        return ResponseEntity.ok(atmService.getAllATMs(atmId));
    }

    @Override
    public ResponseEntity<GetATMNameAndIDResponse> getAtmById(String atmId) {
        return ResponseEntity.ok(atmService.getATmById(atmId));
    }

    @Override
    public ResponseEntity<ATMStatusResponse> getATMStatus() {
        return ResponseEntity.ok(atmService.getATMStatus());
    }

    @Override
    public ResponseEntity<BaseResponse> createATMReportPDF(String atmId) {
        generationFactory.generate("ATM_REPORT_INVOICE", new HashMap<>());
        return null;
    }
}