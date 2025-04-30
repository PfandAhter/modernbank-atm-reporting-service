package com.modernbank.atm_reporting_service.websocket.service.interfaces;

import com.modernbank.atm_reporting_service.websocket.controller.api.response.ATMStatusUpdateResponse;

public interface IATMService {

    ATMStatusUpdateResponse getATMStatusDetail(String atmId);

    void createATMs();
}
