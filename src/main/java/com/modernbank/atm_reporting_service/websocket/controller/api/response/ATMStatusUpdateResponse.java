package com.modernbank.atm_reporting_service.websocket.controller.api.response;

import com.modernbank.atm_reporting_service.model.dto.ATMStatusUpdateDTO;
import com.modernbank.atm_reporting_service.model.enums.ATMDepositStatus;
import com.modernbank.atm_reporting_service.model.enums.ATMWithdrawStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder

public class ATMStatusUpdateResponse extends BaseResponse{

    private ATMStatusUpdateDTO atmStatusDTO;

}