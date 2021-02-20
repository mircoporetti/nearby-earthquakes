package me.mircoporetti.nearbyearthquakes.domain.earthquake.entity;

public class EarthCoordinateBuilder {
    private double lat = 0.0;
    private double lon = 0.0;

    public static EarthCoordinateBuilder anEarthCoordinate() {
        return new EarthCoordinateBuilder();
    }

    public EarthCoordinateBuilder withLat(double lat) {
        this.lat = lat;
        return this;
    }

    public EarthCoordinateBuilder withLon(double lon) {
        this.lon = lon;
        return this;
    }

    public EarthCoordinate build() {
        return new EarthCoordinate(lat, lon);
    }
}
