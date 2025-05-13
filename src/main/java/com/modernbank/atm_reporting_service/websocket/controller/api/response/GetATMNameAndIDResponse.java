package com.modernbank.atm_reporting_service.websocket.controller.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetATMNameAndIDResponse {

    private String id;

    private String name;
}
