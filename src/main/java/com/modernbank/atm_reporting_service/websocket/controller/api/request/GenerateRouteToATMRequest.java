package com.modernbank.atm_reporting_service.websocket.controller.api.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerateRouteToATMRequest {
    private String userId;
    private String userEmail;
    private String selectedAtmId;
    private String bankName;
    private String selectedAtmLatitude;
    private String selectedAtmLongitude;
    private String userLatitude;
    private String userLongitude;
}