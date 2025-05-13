package com.modernbank.atm_reporting_service.websocket.controller.api.response;

import lombok.*;

@Getter
@Setter
@Builder
public class PDFGeneratorResponse extends BaseResponse{
    private String requestId;

    private String estimatedTime;
}