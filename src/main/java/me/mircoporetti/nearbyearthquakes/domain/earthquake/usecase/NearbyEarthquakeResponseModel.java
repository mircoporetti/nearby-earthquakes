package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

import java.util.Objects;

public class NearbyEarthquakeResponseModel {

    private final double magnitude;
    private final String place;
    private final int distance;

    public NearbyEarthquakeResponseModel(double magnitude, String place, int distance) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NearbyEarthquakeResponseModel that = (NearbyEarthquakeResponseModel) o;
        return Double.compare(that.magnitude, magnitude) == 0 && distance == that.distance && Objects.equals(place, that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(magnitude, place, distance);
    }

    @Override
    public String toString() {
        return "EarthquakeResponseModel{" +
                "magnitude=" + magnitude +
                ", place='" + place + '\'' +
                ", distance=" + distance +
                '}';
    }
}
