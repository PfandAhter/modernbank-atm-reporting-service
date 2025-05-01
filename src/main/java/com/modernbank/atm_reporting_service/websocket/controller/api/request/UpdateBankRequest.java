package com.modernbank.atm_reporting_service.websocket.controller.api.request;

import com.modernbank.atm_reporting_service.model.enums.BankStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBankRequest extends CreateBankRequest{

    private Long bankId;

    private BankStatus status;
}
