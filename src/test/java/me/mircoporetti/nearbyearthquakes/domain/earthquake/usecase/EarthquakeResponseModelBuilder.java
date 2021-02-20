package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

public class EarthquakeResponseModelBuilder {
    private int distance = 0;
    private double magnitude = 0;
    private String place = "aPlace";

    public static EarthquakeResponseModelBuilder anEarthquakeResponseModel() {
        return new EarthquakeResponseModelBuilder();
    }

    public EarthquakeResponseModelBuilder withDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public EarthquakeResponseModel build() {
        return new EarthquakeResponseModel(magnitude,place,distance);
    }

    public EarthquakeResponseModelBuilder withMagnitude(double magnitude) {
        this.magnitude = magnitude;
        return this;
    }

    public EarthquakeResponseModelBuilder withPlace(String place) {
        this.place = place;
        return this;
    }
}
