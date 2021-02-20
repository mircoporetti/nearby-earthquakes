package me.mircoporetti.nearbyearthquakes.domain.earthquake;

public class EarthquakeResponseModel {

    private final double magnitude;
    private final String place;
    private int distance;

    public EarthquakeResponseModel(double magnitude, String place, int distance) {
        this.magnitude = magnitude;
        this.place = place;
        this.distance = distance;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public int getDistance() {
        return distance;
    }
}
