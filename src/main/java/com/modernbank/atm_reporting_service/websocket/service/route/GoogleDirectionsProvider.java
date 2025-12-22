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
@ConditionalOnProperty(name = "route.provider", havingValue = "google")
@RequiredArgsConstructor
@Slf4j
public class GoogleDirectionsProvider implements RouteProvider {

    @Value("${route.google.api-key}")
    private String apiKey;

    @Value("${route.google.mode:walking}")
    private String travelMode;

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/directions/json";

    @Override
    public RouteInfo calculateRoute(double startLat, double startLon,
                                    double endLat, double endLon) {
        String url = String.format(
                "%s?origin=%f,%f&destination=%f,%f&mode=%s&key=%s",
                BASE_URL, startLat, startLon, endLat, endLon, travelMode, apiKey
        );

        try {
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);

            if (response == null || !"OK".equals(response.path("status").asText())) {
                log.error("Google Directions API error: {}",
                        response != null ? response.path("status").asText() : "null response");
                return null;
            }

            JsonNode leg = response.path("routes").get(0).path("legs").get(0);
            double distance = leg.path("distance").path("value").asDouble();
            double duration = leg.path("duration").path("value").asDouble();

            return new RouteInfo(distance, duration, getProviderName());
        } catch (Exception e) {
            log.error("Google Directions API call failed", e);
            return null;
        }
    }

    @Override
    public String getProviderName() {
        return "GOOGLE_DIRECTIONS";
    }
}