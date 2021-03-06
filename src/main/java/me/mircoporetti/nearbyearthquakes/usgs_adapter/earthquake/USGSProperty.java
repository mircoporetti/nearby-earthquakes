package me.mircoporetti.nearbyearthquakes.usgs_adapter.earthquake;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class USGSProperty {
    public double mag;
    public String place;

    @Override
    public String toString() {
        return "USGSProperty{" +
                "mag=" + mag +
                ", place='" + place + '\'' +
                '}';
    }
}
