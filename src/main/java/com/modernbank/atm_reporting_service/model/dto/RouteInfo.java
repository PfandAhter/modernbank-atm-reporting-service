package com.modernbank.atm_reporting_service.model.dto;

public record RouteInfo(
        double distanceMeters,
        double durationSeconds,
        String providerName
) {
    public double getDistanceKm() {
        return distanceMeters / 1000.0;
    }

    public int getDurationMinutes() {
        return (int) Math.ceil(durationSeconds / 60.0);
    }
}