package com.modernbank.atm_reporting_service.websocket.service.interfaces;

public interface IHeaderService {
    String extractToken();

    String extractUserId();

    String extractUserRole();
}
