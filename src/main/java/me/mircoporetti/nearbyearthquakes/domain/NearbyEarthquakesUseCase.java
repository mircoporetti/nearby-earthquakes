package me.mircoporetti.nearbyearthquakes.domain;

import java.util.List;

public interface NearbyEarthquakesUseCase {

    List<EarthquakeResponseModel> execute(EarthquakeRequestModel requestModel);
}
