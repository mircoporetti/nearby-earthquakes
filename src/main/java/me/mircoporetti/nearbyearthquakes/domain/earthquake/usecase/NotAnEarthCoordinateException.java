package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

public class NotAnEarthCoordinateException extends RuntimeException {
    public NotAnEarthCoordinateException(String message) {
        super(message);
    }
}
