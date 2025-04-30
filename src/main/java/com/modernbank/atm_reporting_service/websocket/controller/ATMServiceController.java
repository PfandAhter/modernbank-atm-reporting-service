package com.modernbank.atm_reporting_service.websocket.controller;

import com.modernbank.atm_reporting_service.websocket.controller.api.IATMServiceApi;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.ATMStatusUpdateResponse;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IATMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/atm")
//@RequiredArgsConstructor

public class ATMServiceController implements IATMServiceApi {

    private final IATMService atmService;

    public ATMServiceController(IATMService atmService) {
        this.atmService = atmService;
    }

    @Override
    public ResponseEntity<ATMStatusUpdateResponse> getATMStatusDetails(String atmId) {
        atmService.createATMs();
        return ResponseEntity.ok(atmService.getATMStatusDetail(atmId));
    }

}