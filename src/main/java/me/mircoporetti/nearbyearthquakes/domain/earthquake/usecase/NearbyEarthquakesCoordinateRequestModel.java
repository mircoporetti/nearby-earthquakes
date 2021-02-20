package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

import java.util.Objects;

public class NearbyEarthquakesCoordinateRequestModel {

    private final double lat;
    private final double lon;

    public NearbyEarthquakesCoordinateRequestModel(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public boolean isAValidEarthCoordinate() {
        return (lat >= -90 && lat <= 90) && (lon >= -180 && lon <= 180);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NearbyEarthquakesCoordinateRequestModel that = (NearbyEarthquakesCoordinateRequestModel) o;
        return Double.compare(that.lat, lat) == 0 && Double.compare(that.lon, lon) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }
}
