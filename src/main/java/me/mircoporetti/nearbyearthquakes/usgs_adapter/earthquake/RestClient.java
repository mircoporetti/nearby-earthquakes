package me.mircoporetti.nearbyearthquakes.usgs_adapter.earthquake;

public interface RestClient {
    USGSResponse findLastThirtyDaysEarthquakes();
}
