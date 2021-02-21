package me.mircoporetti.nearbyearthquakes.domain.earthquake.entity;

import java.util.Objects;

public class EarthCoordinate {
    private final double lat;
    private final double lon;

    public EarthCoordinate(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public int distanceFrom(EarthCoordinate coordinate) {

        double firstLongitude = Math.toRadians(coordinate.lon);
        double secondLongitude = Math.toRadians(lon);
        double firstLatitude = Math.toRadians(coordinate.lat);
        double secondLatitude = Math.toRadians(lat);

        // Haversine formula
        double longitudeDifference = secondLongitude - firstLongitude;
        double latitudeDifference = secondLatitude - firstLatitude;
        double a = Math.pow(Math.sin(latitudeDifference / 2), 2)
                + Math.cos(firstLatitude) * Math.cos(secondLatitude)
                * Math.pow(Math.sin(longitudeDifference / 2),2);
        double c = 2 * Math.asin(Math.sqrt(a));

        double earthRadius = 6371;

        return (int) (c * earthRadius);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EarthCoordinate that = (EarthCoordinate) o;
        return Double.compare(that.lat, lat) == 0 && Double.compare(that.lon, lon) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }
}
