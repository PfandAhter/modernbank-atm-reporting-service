package com.modernbank.atm_reporting_service.websocket.controller.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CreateATMRequest extends BaseRequest {

    private String name;

    private String latitude;

    private String longitude;

    private String city;

    private String district;

    private String address;

}