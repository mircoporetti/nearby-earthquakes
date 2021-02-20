package me.mircoporetti.nearbyearthquakes.usgsadapter.earthquake;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class USGSGeometry {

    public List<Double> coordinates;

    @Override
    public String toString() {
        return "USGSGeometry{" +
                "coordinates=" + coordinates +
                '}';
    }
}
