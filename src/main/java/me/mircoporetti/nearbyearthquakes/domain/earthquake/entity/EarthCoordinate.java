package me.mircoporetti.nearbyearthquakes.domain.earthquake.entity;

public class EarthCoordinate {
    private double lat;
    private double lon;

    public EarthCoordinate(double lat, double lon) {
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
