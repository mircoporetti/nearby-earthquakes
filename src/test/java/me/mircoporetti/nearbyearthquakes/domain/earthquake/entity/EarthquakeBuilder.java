package me.mircoporetti.nearbyearthquakes.domain.earthquake.entity;

import static me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.EarthCoordinateBuilder.*;

public class EarthquakeBuilder {
    private EarthCoordinate coordinate = anEarthCoordinate().build();
    private int magnitude = 0;
    private String place = "aPlace";

    public static EarthquakeBuilder anEarthquake() {
        return new EarthquakeBuilder();
    }

    public EarthquakeBuilder withCoordinate(EarthCoordinate coordinate) {
        this.coordinate = coordinate;
        return this;
    }

    public EarthquakeBuilder withMagnitude(int magnitude) {
        this.magnitude = magnitude;
        return this;
    }

    public EarthquakeBuilder withPlace(String place) {
        this.place = place;
        return this;
    }

    public Earthquake build() {
        return new Earthquake(coordinate, magnitude, place);
    }
}
