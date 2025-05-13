package com.modernbank.atm_reporting_service.websocket.controller.api;

import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreateATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.UpdateATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IATMControllerApi {

    @GetMapping(path = "/test/status")
    ResponseEntity<ATMStatusUpdateResponse> getATMStatusDetails(@RequestParam("id") String atmId);

    @PostMapping(path = "/create")
    ResponseEntity<BaseResponse> createATM(@RequestBody CreateATMRequest request);

    @PostMapping(path = "/update")
    ResponseEntity<BaseResponse> updateATM(@RequestBody UpdateATMRequest request);

    @GetMapping(path = "/get")
    ResponseEntity<GetAllATMResponse> getAllATMs(@RequestParam("id") String atmId);

    @GetMapping(path = "/get-by-id")
    ResponseEntity<GetATMNameAndIDResponse> getAtmById(@RequestParam("id") String atmId);

    @GetMapping(path = "/get-statuses")
    ResponseEntity<ATMStatusResponse> getATMStatus();

    @GetMapping(path = "/test/report")
    ResponseEntity<BaseResponse> createATMReportPDF(@RequestParam("id") String atmId);

}