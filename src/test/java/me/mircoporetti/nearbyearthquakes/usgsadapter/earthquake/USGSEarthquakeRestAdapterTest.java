package me.mircoporetti.nearbyearthquakes.usgsadapter.earthquake;

import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.Earthquake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.EarthCoordinateBuilder.anEarthCoordinate;
import static me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.EarthquakeBuilder.anEarthquake;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

class USGSEarthquakeRestAdapterTest {

    @Mock
    private RestClient restClient;

    private USGSEarthquakeRestAdapter underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new USGSEarthquakeRestAdapter(restClient);
    }

    @Test
    void noEarthquakes() {
        USGSResponse usgsResponse = new USGSResponse();
        usgsResponse.features = emptyList();

        doReturn(usgsResponse).when(restClient).findLastThirtyDaysEarthquakes();

        List<Earthquake> result = underTest.getLastThirtyDaysEarthquakes();

        assertThat(result.size(), is(0));
    }

    @Test
    void getLastThirtyDaysEarthquakes() {
        USGSResponse usgsResponse = new USGSResponse();
        USGSGeometry usgsGeometry = new USGSGeometry();
        usgsGeometry.coordinates = Arrays.asList(8.4, -50.7, 33.8);
        USGSProperty usgsProperty = new USGSProperty();
        usgsProperty.place = "aPlace";
        usgsProperty.mag = 3.2;
        USGSEarthquake usgsEarthquake = new USGSEarthquake();
        usgsEarthquake.geometry = usgsGeometry;
        usgsEarthquake.properties = usgsProperty;
        usgsResponse.features = singletonList(usgsEarthquake);

        doReturn(usgsResponse).when(restClient).findLastThirtyDaysEarthquakes();

        List<Earthquake> result = underTest.getLastThirtyDaysEarthquakes();

        assertThat(result.get(0), is(anEarthquake()
                .withCoordinate(anEarthCoordinate().withLat(-50.7).withLon(8.4).build())
                .withMagnitude(3.2)
                .withPlace("aPlace")
                .build()));
    }
}