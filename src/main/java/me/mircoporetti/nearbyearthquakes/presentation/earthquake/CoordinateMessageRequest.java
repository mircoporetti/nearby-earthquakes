package me.mircoporetti.nearbyearthquakes.presentation.earthquake;

public class CoordinateMessageRequest {
    private final String lat;
    private final String lon;

    public CoordinateMessageRequest(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
