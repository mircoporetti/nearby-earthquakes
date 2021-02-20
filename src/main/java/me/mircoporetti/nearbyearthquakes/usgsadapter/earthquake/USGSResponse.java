package me.mircoporetti.nearbyearthquakes.usgsadapter.earthquake;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class USGSResponse {
    public List<USGSEarthquake> features;

    @Override
    public String toString() {
        return "USGSResponse{" +
                "features=" + features +
                '}';
    }
}
