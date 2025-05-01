package com.modernbank.atm_reporting_service.websocket.controller.api.request;

import com.modernbank.atm_reporting_service.model.enums.ATMDepositStatus;
import com.modernbank.atm_reporting_service.model.enums.ATMStatus;
import com.modernbank.atm_reporting_service.model.enums.ATMWithdrawStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter

public class UpdateATMRequest extends BaseRequest{

    private String atmId;

    private List<String> supportedBanks;

    private ATMStatus status;

    private ATMDepositStatus depositStatus;

    private ATMWithdrawStatus withdrawStatus;
}