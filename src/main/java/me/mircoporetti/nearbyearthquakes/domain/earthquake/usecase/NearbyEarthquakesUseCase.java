package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

import java.util.List;

public interface NearbyEarthquakesUseCase {

    List<NearbyEarthquakeResponseModel> execute(NearbyEarthquakesCoordinateRequestModel requestModel);
}
