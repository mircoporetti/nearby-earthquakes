package me.mircoporetti.nearbyearthquakes.usgsadapter.earthquake;

public interface RestClient {
    USGSResponse findLastThirtyDaysEarthquakes();
}
