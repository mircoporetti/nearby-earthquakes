package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.Earthquake;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.port.USGSEarthquakePort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class NearbyEarthquakes implements NearbyEarthquakesUseCase{
    private final USGSEarthquakePort usgsEarthquakePort;

    public NearbyEarthquakes(USGSEarthquakePort usgsEarthquakePort) {
        this.usgsEarthquakePort = usgsEarthquakePort;
    }

    @Override
    public List<EarthquakeResponseModel> execute(NearbyEarthquakesCoordinateRequestModel coordinateRequestModel) {
        if(coordinateRequestModel.isAValidEarthCoordinate()){
            List<Earthquake> lastThirtyDaysEarthquakes = usgsEarthquakePort.getLastThirtyDaysEarthquakes();

            return Collections.emptyList();
        }else{
            throw new NotAnEarthCoordinateException(
                    String.format("Given lat: %s lon: %s is not a valid earth coordinate",
                            coordinateRequestModel.getLat(), coordinateRequestModel.getLon())
            );
        }
    }
}
