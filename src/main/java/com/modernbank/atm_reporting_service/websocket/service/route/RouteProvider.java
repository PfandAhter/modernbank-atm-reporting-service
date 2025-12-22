package com.modernbank.atm_reporting_service.websocket.service.route;

import com.modernbank.atm_reporting_service.model.dto.RouteInfo;

public interface RouteProvider {
    RouteInfo calculateRoute(double startLat, double startLon, double endLat, double endLon);
    String getProviderName();
}