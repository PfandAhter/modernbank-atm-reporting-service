package com.modernbank.atm_reporting_service.api.client;

import com.modernbank.atm_reporting_service.api.request.ChatNotificationSendRequest;
import com.modernbank.atm_reporting_service.api.request.NotificationMessage;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "notification-service", url = "${feign.client.notification-service.url}")
public interface NotificationServiceClient {

    @PostMapping(value = "${feign.client.notification-service.sendNotificationRequest}")
    BaseResponse sendNotification (@RequestBody NotificationMessage notificationMessage);

    @PostMapping(value = "${feign.client.notification-service.sendRouteGenerateRequest}")
    BaseResponse sendRouteGenerateRequest (@RequestBody ChatNotificationSendRequest chatNotificationSendRequest);
}