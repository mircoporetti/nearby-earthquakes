package me.mircoporetti.nearbyearthquakes.presenter;

import me.mircoporetti.nearbyearthquakes.domain.EarthquakeRequestModel;
import me.mircoporetti.nearbyearthquakes.domain.EarthquakeResponseModel;
import me.mircoporetti.nearbyearthquakes.domain.NearbyEarthquakesUseCase;

import java.util.List;
import java.util.stream.Collectors;

public class EarthquakePresenter {

    private final NearbyEarthquakesUseCase nearbyEarthquakesUseCase;

    public EarthquakePresenter(NearbyEarthquakesUseCase nearbyEarthquakesUseCase) {
        this.nearbyEarthquakesUseCase = nearbyEarthquakesUseCase;
    }

    public List<String> getNearbyEarthquakes(String lat, String lon) {
        EarthquakeRequestModel requestModel;
        try {
           requestModel = new EarthquakeRequestModel(Double.parseDouble(lat), Double.parseDouble(lon));
        }catch(NumberFormatException e){
            throw new CoordinateFormatException(e.getMessage());
        }

        List<EarthquakeResponseModel> nearbyEarthquakes =
                nearbyEarthquakesUseCase.execute(requestModel);

        return nearbyEarthquakes.stream().map(
                earthquake -> "M " + earthquake.getMagnitude() + " - " + earthquake.getPlace() + " || " + earthquake.getDistance())
                .collect(Collectors.toList());
    }
}
