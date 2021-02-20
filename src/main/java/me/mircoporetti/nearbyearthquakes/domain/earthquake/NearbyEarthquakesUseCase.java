package me.mircoporetti.nearbyearthquakes.domain.earthquake;

import java.util.List;

public interface NearbyEarthquakesUseCase {

    List<EarthquakeResponseModel> execute(EarthquakeRequestModel requestModel);
}
