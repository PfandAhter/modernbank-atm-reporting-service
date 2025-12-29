package com.modernbank.atm_reporting_service.websocket.service.interfaces;

import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreateBankRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.UpdateBankRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.BaseResponse;

public interface IBankService {

    BaseResponse createBank(CreateBankRequest request);

    BaseResponse activateBank(UpdateBankRequest request) ;

    BaseResponse updateBank(UpdateBankRequest request);
}
