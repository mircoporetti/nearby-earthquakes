package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

import java.util.List;

public class NearbyEarthquakes implements NearbyEarthquakesUseCase{
    @Override
    public List<EarthquakeResponseModel> execute(NearbyEarthquakesCoordinateRequestModel coordinateRequestModel) {
        if(coordinateRequestModel.isAValidEarthCoordinate()){
            return null;
        }else{
            throw new NotAnEarthCoordinateException(
                    String.format("Given lat: %s lon: %s is not a valid earth coordinate",
                            coordinateRequestModel.getLat(), coordinateRequestModel.getLon())
            );
        }
    }
}
