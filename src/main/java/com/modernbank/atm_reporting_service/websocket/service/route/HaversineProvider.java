package com.modernbank.atm_reporting_service.websocket.service.route;

import com.modernbank.atm_reporting_service.model.dto.RouteInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "route.provider", havingValue = "haversine", matchIfMissing = true)
@Slf4j
public class HaversineProvider implements RouteProvider {

    private static final int EARTH_RADIUS_METERS = 6371000;
    private static final double WALKING_SPEED_MPS = 1.4; // ~5 km/h

    @Override
    public RouteInfo calculateRoute(double startLat, double startLon,
                                    double endLat, double endLon) {
        double phi1 = Math.toRadians(startLat);
        double phi2 = Math.toRadians(endLat);
        double dPhi = Math.toRadians(endLat - startLat);
        double dLambda = Math.toRadians(endLon - startLon);

        double a = Math.sin(dPhi / 2) * Math.sin(dPhi / 2) +
                Math.cos(phi1) * Math.cos(phi2) *
                        Math.sin(dLambda / 2) * Math.sin(dLambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS_METERS * c;
        double duration = distance / WALKING_SPEED_MPS;

        return new RouteInfo(distance, duration, getProviderName());
    }

    @Override
    public String getProviderName() {
        return "HAVERSINE";
    }
}
