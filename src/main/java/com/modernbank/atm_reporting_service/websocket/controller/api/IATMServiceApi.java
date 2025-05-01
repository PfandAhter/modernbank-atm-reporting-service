package com.modernbank.atm_reporting_service.websocket.controller.api;

import com.modernbank.atm_reporting_service.websocket.controller.api.response.ATMStatusUpdateResponse;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface IATMServiceApi {

    @GetMapping(path = "/test/status")
    ResponseEntity<ATMStatusUpdateResponse> getATMStatusDetails(@RequestParam("id") String atmId);

    @GetMapping(path = "/test/report")
    ResponseEntity<BaseResponse> createATMReportPDF(@RequestParam("id") String atmId);

}