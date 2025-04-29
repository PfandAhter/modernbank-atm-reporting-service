package com.modernbank.atm_reporting_service.websocket.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ATMWebSocketController {

    @MessageMapping("/atm-status")
    @SendTo("/topic/atm-status-updates")
    public String handleATMStatusUpdate(String message) {
        return "ATM Status Update: " + message;
    }
}