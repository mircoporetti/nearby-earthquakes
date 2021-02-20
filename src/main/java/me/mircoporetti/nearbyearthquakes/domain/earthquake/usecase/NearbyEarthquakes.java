package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.Earthquake;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.port.USGSEarthquakePort;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class NearbyEarthquakes implements NearbyEarthquakesUseCase{
    private final USGSEarthquakePort usgsEarthquakePort;

    public NearbyEarthquakes(USGSEarthquakePort usgsEarthquakePort) {
        this.usgsEarthquakePort = usgsEarthquakePort;
    }

    @Override
    public List<EarthquakeResponseModel> execute(NearbyEarthquakesCoordinateRequestModel coordinateRequestModel) {
        if(coordinateRequestModel.isAValidEarthCoordinate()){
            List<Earthquake> lastThirtyDaysEarthquakes = usgsEarthquakePort.getLastThirtyDaysEarthquakes();
                if(lastThirtyDaysEarthquakes.isEmpty()){
                    return emptyList();
                } else{
                    EarthquakeResponseModel earthquakeResponse = new EarthquakeResponseModel(
                            lastThirtyDaysEarthquakes.get(0).getMagnitude(),
                            lastThirtyDaysEarthquakes.get(0).getPlace(),
                            lastThirtyDaysEarthquakes.get(0).calculateDistanceFrom(coordinateRequestModel.getLat(), coordinateRequestModel.getLon()));
                    return singletonList(earthquakeResponse);
                }
        }else{
            throw new NotAnEarthCoordinateException(
                    String.format("Given lat: %s lon: %s is not a valid earth coordinate",
                            coordinateRequestModel.getLat(), coordinateRequestModel.getLon())
            );
        }
    }
}
