package me.mircoporetti.nearbyearthquakes.domain.earthquake.entity;

import java.util.Objects;

public class Earthquake {


    private final EarthCoordinate coordinate;
    private final double magnitude;
    private final String place;

    public Earthquake(EarthCoordinate coordinate, double magnitude, String place) {
        this.coordinate = coordinate;
        this.magnitude = magnitude;
        this.place = place;
    }

    public EarthCoordinate getCoordinate() {
        return coordinate;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public int calculateDistanceFrom(EarthCoordinate coordinate) {
        return this.coordinate.distanceFrom(coordinate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Earthquake that = (Earthquake) o;
        return Double.compare(that.magnitude, magnitude) == 0 && Objects.equals(coordinate, that.coordinate) && Objects.equals(place, that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, magnitude, place);
    }
}
