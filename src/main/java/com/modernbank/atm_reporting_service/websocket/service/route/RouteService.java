package com.modernbank.atm_reporting_service.websocket.service.route;

import com.modernbank.atm_reporting_service.model.dto.RouteInfo;

public interface RouteService {
    RouteInfo getRoute(double startLat, double startLon,
                       double endLat, double endLon);

    String getActiveProvider();
}