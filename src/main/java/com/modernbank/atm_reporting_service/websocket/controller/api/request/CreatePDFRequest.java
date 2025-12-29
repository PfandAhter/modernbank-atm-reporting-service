package com.modernbank.atm_reporting_service.websocket.controller.api.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CreatePDFRequest {

    @JsonIgnore
    private String requestId;

    private String generationType;

    private Map<String, Object> details = new HashMap<>();
}