package com.modernbank.atm_reporting_service.websocket.controller.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ATMStatusResponse extends BaseResponse{
    private List<String> banks;
    private List<String> statuses;
    private List<String> depositStatuses;
    private List<String> withdrawStatuses;
}