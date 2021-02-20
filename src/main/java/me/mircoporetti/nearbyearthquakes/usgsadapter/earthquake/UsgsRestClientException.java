package me.mircoporetti.nearbyearthquakes.usgsadapter.earthquake;

public class UsgsRestClientException extends RuntimeException {
    public UsgsRestClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
