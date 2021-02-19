package me.mircoporetti.nearbyearthquakes.domain;

public class EarthquakeRequestModel {

    private final double lat;
    private final double lon;

    public EarthquakeRequestModel(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
