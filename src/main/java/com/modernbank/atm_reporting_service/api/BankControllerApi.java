package com.modernbank.atm_reporting_service.api;

import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreateBankRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.UpdateBankRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BankControllerApi {

    @PostMapping(path = "/create")
    ResponseEntity<BaseResponse> createBAnk(@RequestBody CreateBankRequest request);

    @PostMapping(path = "/update")
    ResponseEntity<BaseResponse> updateBank(@RequestBody UpdateBankRequest request);

    @PostMapping(path = "/activate")
    ResponseEntity<BaseResponse> activateBank(@RequestBody UpdateBankRequest request);
}
