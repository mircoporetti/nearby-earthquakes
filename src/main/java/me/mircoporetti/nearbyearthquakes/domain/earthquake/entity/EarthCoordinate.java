package me.mircoporetti.nearbyearthquakes.domain.earthquake.entity;

import java.util.Objects;

public class EarthCoordinate {
    private final double lat;
    private final double lon;

    public EarthCoordinate(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public int distanceFrom(double requestedLat, double requestedLon) {

            double lon1 = Math.toRadians(requestedLon);
            double lon2 = Math.toRadians(lon);
            double lat1 = Math.toRadians(requestedLat);
            double lat2 = Math.toRadians(lat);

            double dlon = lon2 - lon1;
            double dlat = lat2 - lat1;
            double a = Math.pow(Math.sin(dlat / 2), 2)
                    + Math.cos(lat1) * Math.cos(lat2)
                    * Math.pow(Math.sin(dlon / 2),2);

            double c = 2 * Math.asin(Math.sqrt(a));
            double r = 6371;

            return (int) (c * r);
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
