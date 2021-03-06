package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.EarthCoordinate;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.Earthquake;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.port.USGSEarthquakePort;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NearbyEarthquakes implements NearbyEarthquakesUseCase{
    private final USGSEarthquakePort usgsEarthquakePort;

    public NearbyEarthquakes(USGSEarthquakePort usgsEarthquakePort) {
        this.usgsEarthquakePort = usgsEarthquakePort;
    }

    @Override
    public List<NearbyEarthquakeResponseModel> execute(NearbyEarthquakesCoordinateRequestModel coordinateRequest) {
        if(coordinateRequest.isAValidEarthCoordinate()){
            List<Earthquake> lastThirtyDaysEarthquakes = usgsEarthquakePort.getLastThirtyDaysEarthquakes();
            return lastThirtyDaysEarthquakes
                    .stream()
                    .filter(distinctByKey(Earthquake::getCoordinate))
                    .map(mapToUseCaseResponseModel(coordinateRequest))
                    .sorted(Comparator.comparingInt(NearbyEarthquakeResponseModel::getDistance))
                    .limit(10)
                    .collect(Collectors.toList());
        }else{
            throw new NotAnEarthCoordinateException(
                    String.format("Given lat: %s lon: %s is not a valid earth coordinate",
                            coordinateRequest.getLat(), coordinateRequest.getLon())
            );
        }
    }

    private Function<Earthquake, NearbyEarthquakeResponseModel> mapToUseCaseResponseModel(NearbyEarthquakesCoordinateRequestModel coordinateRequest) {
        return earthquake -> new NearbyEarthquakeResponseModel(
                earthquake.getMagnitude(),
                earthquake.getPlace(),
                earthquake.calculateDistanceFrom(
                        new EarthCoordinate(coordinateRequest.getLat(), coordinateRequest.getLon())
                ));
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
