package com.modernbank.atm_reporting_service.websocket.service.interfaces;

import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreateATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.UpdateATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.*;

public interface IATMService {

    ATMStatusUpdateResponse getATMStatusDetail(String atmId);

    BaseResponse createATM(CreateATMRequest request);

    BaseResponse updateATM(UpdateATMRequest request);

    GetAllATMResponse getAllATMs(String atmId);

    ATMStatusResponse getATMStatus();

    GetATMNameAndIDResponse getATmById(String atmId);
}
