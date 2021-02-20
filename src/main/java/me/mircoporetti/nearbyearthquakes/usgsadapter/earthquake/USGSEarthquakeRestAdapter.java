package me.mircoporetti.nearbyearthquakes.usgsadapter.earthquake;

import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.EarthCoordinate;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.Earthquake;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.port.USGSEarthquakePort;

import java.util.List;
import java.util.stream.Collectors;

public class USGSEarthquakeRestAdapter implements USGSEarthquakePort {

    private final RestClient restClient;

    public USGSEarthquakeRestAdapter(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<Earthquake> getLastThirtyDaysEarthquakes() {
        USGSResponse usgsResponse = restClient.findLastThirtyDaysEarthquakes();

        return usgsResponse.features.stream().map(
                usgsEarthquake -> new Earthquake(
                        new EarthCoordinate(
                                usgsEarthquake.geometry.coordinates.get(1),
                                usgsEarthquake.geometry.coordinates.get(0)
                        ),
                        usgsEarthquake.properties.mag,
                        usgsEarthquake.properties.place
                )).collect(Collectors.toList());
    }
}
