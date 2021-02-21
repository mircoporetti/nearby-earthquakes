package me.mircoporetti.nearbyearthquakes.presentation.earthquake;

import me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase.NearbyEarthquakesCoordinateRequestModel;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase.NearbyEarthquakeResponseModel;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase.NearbyEarthquakesUseCase;

import java.util.List;
import java.util.stream.Collectors;

/*
    The presenter interacts with the domain to execute a particular use case.
    This kind of approach allows you to consider the infrastructure layers as plugins
    of the application business logic. The main goal is to leave the domain independent.
 */
public class EarthquakePresenter {

    private final NearbyEarthquakesUseCase nearbyEarthquakesUseCase;

    public EarthquakePresenter(NearbyEarthquakesUseCase nearbyEarthquakesUseCase) {
        this.nearbyEarthquakesUseCase = nearbyEarthquakesUseCase;
    }

    public List<String> getNearbyEarthquakes(CoordinateMessageRequest messageRequest) {
        NearbyEarthquakesCoordinateRequestModel requestModel;
        try {
           requestModel = new NearbyEarthquakesCoordinateRequestModel(Double.parseDouble(messageRequest.getLat()), Double.parseDouble(messageRequest.getLon()));
        }catch(NumberFormatException e){
            throw new CoordinateFormatException("Lat & Lon must be numeric!");
        }

        List<NearbyEarthquakeResponseModel> nearbyEarthquakes =
                nearbyEarthquakesUseCase.execute(requestModel);

        return nearbyEarthquakes.stream().map(
                earthquake -> String.format(
                        "M %s - %s || %d", earthquake.getMagnitude(), earthquake.getPlace(), earthquake.getDistance()
                )).collect(Collectors.toList());
    }
}
