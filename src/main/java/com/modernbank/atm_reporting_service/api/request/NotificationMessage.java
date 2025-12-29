package com.modernbank.atm_reporting_service.api.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationMessage {
    private String userId;

    private String message;

    private String type;

    private String title;
}