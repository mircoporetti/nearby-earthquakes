package me.mircoporetti.nearbyearthquakes.domain.earthquake.entity;

public class Earthquake {


    private final EarthCoordinate coordinate;
    private final double magnitude;
    private final String place;

    public Earthquake(EarthCoordinate coordinate, int magnitude, String place) {
        this.coordinate = coordinate;
        this.magnitude = magnitude;
        this.place = place;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public int calculateDistanceFrom(double lat, double lon) {
        return coordinate.distanceFrom(lat,lon);
    }
}
