package com.modernbank.atm_reporting_service.websocket.service.route;

import com.modernbank.atm_reporting_service.model.dto.RouteInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RouteServiceImpl implements RouteService{

    private final RouteProvider routeProvider;

    public RouteInfo getRoute(double startLat, double startLon,
                              double endLat, double endLon) {
        log.info("Calculating route using provider: {}", routeProvider.getProviderName());
        return routeProvider.calculateRoute(startLat, startLon, endLat, endLon);
    }

    public String getActiveProvider() {
        return routeProvider.getProviderName();

    }
}