package com.modernbank.atm_reporting_service.websocket.service.interfaces;

import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreateATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.UpdateATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.ATMStatusUpdateResponse;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.BaseResponse;

public interface IATMService {

    ATMStatusUpdateResponse getATMStatusDetail(String atmId);

    BaseResponse createATM(CreateATMRequest request);

    BaseResponse updateATM(UpdateATMRequest request);

}
