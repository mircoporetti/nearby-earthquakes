package me.mircoporetti.nearbyearthquakes.presenter;

import me.mircoporetti.nearbyearthquakes.domain.EarthquakeRequestModel;
import me.mircoporetti.nearbyearthquakes.domain.EarthquakeResponseModel;
import me.mircoporetti.nearbyearthquakes.domain.NearbyEarthquakesUseCase;

import java.util.List;
import java.util.stream.Collectors;

public class EarthquakePresenter {

    private NearbyEarthquakesUseCase nearbyEarthquakesUseCase;

    public EarthquakePresenter(NearbyEarthquakesUseCase nearbyEarthquakesUseCase) {
        this.nearbyEarthquakesUseCase = nearbyEarthquakesUseCase;
    }

    public List<String> getNearbyEarthquakes(double lat, double lon) {
        List<EarthquakeResponseModel> nearbyEarthquakes = nearbyEarthquakesUseCase.execute(new EarthquakeRequestModel(lat, lon));

        return nearbyEarthquakes.stream().map(
                earthquake -> "M " + earthquake.getMagnitude() + " - " + earthquake.getPlace() + " || " + earthquake.getDistance())
                .collect(Collectors.toList());
    }
}
