package com.modernbank.atm_reporting_service.websocket.controller.api.response;

import com.modernbank.atm_reporting_service.model.dto.ATMDTO;
import com.modernbank.atm_reporting_service.model.dto.ATMStatusUpdateDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder

public class GetAllATMResponse extends BaseResponse{
    List<ATMDTO> atmStatusDTOList;
}