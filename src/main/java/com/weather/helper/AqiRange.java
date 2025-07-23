package com.weather.helper;

public enum AqiRange {
    GOOD(0, 50, "Good"),
    MODERATE(51, 100, "Moderate"),
    UNHEALTHY_SENSITIVE(101, 150, "Unhealthy for Sensitive Groups"),
    UNHEALTHY(151, 200, "Unhealthy"),
    VERY_UNHEALTHY(201, 300, "Very Unhealthy"),
    HAZARDOUS(301, 500, "Hazardous");

    private final int min;
    private final int max;
    private final String description;

    AqiRange(int min, int max, String description) {
        this.min = min;
        this.max = max;
        this.description = description;
    }

    public static String fromAqi(int aqi) {
        for (AqiRange range : values()) {
            if (aqi >= range.min && aqi <= range.max) {
                return range.description;
            }
        }
        return "Invalid AQI";
    }
}
