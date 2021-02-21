package me.mircoporetti.nearbyearthquakes.usgs_adapter.earthquake;

import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.EarthCoordinate;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.Earthquake;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.port.USGSEarthquakePort;

import java.util.List;
import java.util.stream.Collectors;

/*
    In Clean Architecture it would be ideal to interact with the use cases
    through a domain response model. In this particular case,
    I chose to return the domain entity directly because it would be identical
    to the that model. It's a compromise :)
*/
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
