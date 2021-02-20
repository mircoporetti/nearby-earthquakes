package me.mircoporetti.nearbyearthquakes.domain.earthquake;

import java.util.Objects;

public class EarthquakeRequestModel {

    private final double lat;
    private final double lon;

    public EarthquakeRequestModel(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EarthquakeRequestModel that = (EarthquakeRequestModel) o;
        return Double.compare(that.lat, lat) == 0 && Double.compare(that.lon, lon) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }
}
