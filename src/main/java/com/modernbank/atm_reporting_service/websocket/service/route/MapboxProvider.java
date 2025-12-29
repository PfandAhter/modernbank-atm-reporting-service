package com.modernbank.atm_reporting_service.websocket.service.route;

import com.fasterxml.jackson.databind.JsonNode;
import com.modernbank.atm_reporting_service.model.dto.RouteInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConditionalOnProperty(name = "route.provider", havingValue = "mapbox")
@RequiredArgsConstructor
@Slf4j
public class MapboxProvider implements RouteProvider {

    @Value("${route.mapbox.access-token}")
    private String accessToken;

    @Value("${route.mapbox.profile:walking}")
    private String profile;

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://api.mapbox.com/directions/v5/mapbox";

    @Override
    public RouteInfo calculateRoute(double startLat, double startLon,
                                    double endLat, double endLon) {
        // Mapbox format: longitude,latitude (ters sıra!)
        String url = String.format(
                "%s/%s/%f,%f;%f,%f?access_token=%s&overview=false",
                BASE_URL, profile, startLon, startLat, endLon, endLat, accessToken
        );

        try {
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);

            if (response == null || !"Ok".equals(response.path("code").asText())) {
                log.error("Mapbox API error: {}",
                        response != null ? response.path("code").asText() : "null response");
                return null;
            }

            JsonNode route = response.path("routes").get(0);
            double distance = route.path("distance").asDouble();
            double duration = route.path("duration").asDouble();

            return new RouteInfo(distance, duration, getProviderName());
        } catch (Exception e) {
            log.error("Mapbox API call failed", e);
            return null;
        }
    }

    @Override
    public String getProviderName() {
        return "MAPBOX";
    }
}