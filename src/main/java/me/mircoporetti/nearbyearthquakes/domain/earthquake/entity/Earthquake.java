package me.mircoporetti.nearbyearthquakes.domain.earthquake.entity;

public class Earthquake {


    private EarthCoordinate coordinate;
    private int magnitude;
    private String place;

    public Earthquake(EarthCoordinate coordinate, int magnitude, String place) {
        this.coordinate = coordinate;
        this.magnitude = magnitude;
        this.place = place;
    }

    public EarthCoordinate getCoordinate() {
        return coordinate;
    }

    public int getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }
}
