package me.mircoporetti.nearbyearthquakes.domain.earthquake.port;

import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.Earthquake;

import java.util.List;

public interface USGSEarthquakePort {

    List<Earthquake> getLastThirtyDaysEarthquakes();

}
