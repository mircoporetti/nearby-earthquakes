package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

import java.util.Objects;

public class EarthquakeResponseModel {

    private double magnitude;
    private String place;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EarthquakeResponseModel that = (EarthquakeResponseModel) o;
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
