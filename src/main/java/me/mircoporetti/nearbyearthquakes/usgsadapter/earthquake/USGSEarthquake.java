package me.mircoporetti.nearbyearthquakes.usgsadapter.earthquake;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class USGSEarthquake {

    public USGSProperty properties;
    public USGSGeometry geometry;

    @Override
    public String toString() {
        return "USGSEarthquake{" +
                "properties=" + properties +
                ", geometry=" + geometry +
                '}';
    }
}
