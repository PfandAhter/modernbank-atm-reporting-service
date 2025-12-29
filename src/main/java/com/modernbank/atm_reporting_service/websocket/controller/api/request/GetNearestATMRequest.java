package com.modernbank.atm_reporting_service.websocket.controller.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetNearestATMRequest {
    private String latitude;
    private String longitude;

    private String bankName;
}