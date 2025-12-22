package com.modernbank.atm_reporting_service.websocket.controller;

import com.modernbank.atm_reporting_service.api.BankControllerApi;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreateBankRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.UpdateBankRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.BaseResponse;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IBankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/bank")
public class BankServiceController implements BankControllerApi {

    private final IBankService bankService;

    public BankServiceController(IBankService bankService){
        this.bankService = bankService;
    }

    @Override
    public ResponseEntity<BaseResponse> createBAnk(CreateBankRequest request) {
        return ResponseEntity.ok(bankService.createBank(request));
    }

    @Override
    public ResponseEntity<BaseResponse> updateBank(UpdateBankRequest request) {
        return ResponseEntity.ok(bankService.updateBank(request));
    }

    @Override
    public ResponseEntity<BaseResponse> activateBank(UpdateBankRequest request) {
        return ResponseEntity.ok(bankService.activateBank(request));
    }
}