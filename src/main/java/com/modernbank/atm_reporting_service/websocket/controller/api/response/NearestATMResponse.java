package com.modernbank.atm_reporting_service.websocket.controller.api.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NearestATMResponse {
    private String selectedAtmId;
    private String bankName;
    private String selectedAtmLatitude;
    private String selectedAtmLongitude;
    private String userLatitude;
    private String userLongitude;
}