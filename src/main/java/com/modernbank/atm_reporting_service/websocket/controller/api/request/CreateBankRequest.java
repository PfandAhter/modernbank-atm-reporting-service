package com.modernbank.atm_reporting_service.websocket.controller.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBankRequest extends BaseRequest{

    private String name;

}