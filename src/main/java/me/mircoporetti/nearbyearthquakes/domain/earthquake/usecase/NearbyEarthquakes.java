package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.Earthquake;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.port.USGSEarthquakePort;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NearbyEarthquakes implements NearbyEarthquakesUseCase{
    private final USGSEarthquakePort usgsEarthquakePort;

    public NearbyEarthquakes(USGSEarthquakePort usgsEarthquakePort) {
        this.usgsEarthquakePort = usgsEarthquakePort;
    }

    @Override
    public List<NearbyEarthquakeResponseModel> execute(NearbyEarthquakesCoordinateRequestModel coordinateRequestModel) {
        if(coordinateRequestModel.isAValidEarthCoordinate()){
            List<Earthquake> lastThirtyDaysEarthquakes = usgsEarthquakePort.getLastThirtyDaysEarthquakes();
                return lastThirtyDaysEarthquakes
                        .stream()
                        .map(earthquake -> new NearbyEarthquakeResponseModel(
                                earthquake.getMagnitude(),
                                earthquake.getPlace(),
                                earthquake.calculateDistanceFrom(coordinateRequestModel.getLat(), coordinateRequestModel.getLon())))
                        .sorted(Comparator.comparingInt(NearbyEarthquakeResponseModel::getDistance))
                        .limit(10)
                        .collect(Collectors.toList());
        }else{
            throw new NotAnEarthCoordinateException(
                    String.format("Given lat: %s lon: %s is not a valid earth coordinate",
                            coordinateRequestModel.getLat(), coordinateRequestModel.getLon())
            );
        }
    }
}
