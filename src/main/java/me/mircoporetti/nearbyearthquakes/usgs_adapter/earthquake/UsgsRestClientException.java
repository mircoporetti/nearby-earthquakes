package me.mircoporetti.nearbyearthquakes.usgs_adapter.earthquake;

public class UsgsRestClientException extends RuntimeException {
    public UsgsRestClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
