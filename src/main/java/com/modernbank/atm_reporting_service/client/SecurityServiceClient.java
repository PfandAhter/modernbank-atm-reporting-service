package com.modernbank.atm_reporting_service.client;

import com.modernbank.atm_reporting_service.websocket.controller.api.request.BaseRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "security-service", url = "${feign.client.security-service.url}")
public interface SecurityServiceClient {

    @PostMapping("${feign.client.security-service.extractUserCodeFromToken}")
    String extractUserIdFromToken(BaseRequest baseRequest);
}