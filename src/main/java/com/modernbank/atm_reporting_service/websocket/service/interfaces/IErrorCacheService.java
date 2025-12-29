package com.modernbank.atm_reporting_service.websocket.service.interfaces;

import com.modernbank.atm_reporting_service.model.entity.ErrorCodes;

import java.util.List;

public interface IErrorCacheService {

    List<ErrorCodes> getAllErrorCodes();

    ErrorCodes getErrorCode(String errorCodeId);

    void refreshErrorCodesCache();
}
