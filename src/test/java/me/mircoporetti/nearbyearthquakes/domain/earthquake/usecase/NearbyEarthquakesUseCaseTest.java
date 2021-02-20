package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.EarthCoordinate;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.Earthquake;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.EarthquakeBuilder;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.port.USGSEarthquakePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.EarthCoordinateBuilder.*;
import static me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.EarthquakeBuilder.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class NearbyEarthquakesUseCaseTest {
    @Mock
    USGSEarthquakePort usgsEarthquakePort;

    private NearbyEarthquakes underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new NearbyEarthquakes(usgsEarthquakePort);
    }

    @Test
    void notValidCoordinate() {
        NearbyEarthquakesCoordinateRequestModel givenCoordinate = new NearbyEarthquakesCoordinateRequestModel(-91, 181);

        assertThrows(NotAnEarthCoordinateException.class, () -> underTest.execute(givenCoordinate));
    }

    @Test
    void noEarthquakesFound() {
        doReturn(emptyList()).when(usgsEarthquakePort).getLastThirtyDaysEarthquakes();

        NearbyEarthquakesCoordinateRequestModel givenCoordinate = new NearbyEarthquakesCoordinateRequestModel(0.0, 0.0);

        List<EarthquakeResponseModel> result = underTest.execute(givenCoordinate);

        verify(usgsEarthquakePort).getLastThirtyDaysEarthquakes();
        assertThat(result, is(emptyList()));
    }

    @Test
    void anEarthquakeWithSameCoordinate() {
        doReturn(singletonList(new Earthquake(new EarthCoordinate(0.0, 0.0), 4, "Somewhere")))
                .when(usgsEarthquakePort).getLastThirtyDaysEarthquakes();

        NearbyEarthquakesCoordinateRequestModel givenCoordinate = new NearbyEarthquakesCoordinateRequestModel(0.0, 0.0);

        List<EarthquakeResponseModel> result = underTest.execute(givenCoordinate);

        assertThat(result, is(singletonList(new EarthquakeResponseModel(4, "Somewhere", 0))));
    }

    @Test
    void anEarthquakeWithDifferentCoordinate() {
        doReturn(singletonList(new Earthquake(new EarthCoordinate(-60.0, 10.3), 4, "Somewhere")))
                .when(usgsEarthquakePort).getLastThirtyDaysEarthquakes();

        NearbyEarthquakesCoordinateRequestModel givenCoordinate = new NearbyEarthquakesCoordinateRequestModel(-40.3, 4.2);

        List<EarthquakeResponseModel> result = underTest.execute(givenCoordinate);

        assertThat(result, is(singletonList(new EarthquakeResponseModel(4, "Somewhere", 2230))));
    }

    @Test
    void twoEarthquakesWithDifferentCoordinate() {
        doReturn(asList(
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(-60.0).withLon(10.3).build()).build(),
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(-60.0).withLon(4.3).build()).build()
        )).when(usgsEarthquakePort).getLastThirtyDaysEarthquakes();

        NearbyEarthquakesCoordinateRequestModel givenCoordinate = new NearbyEarthquakesCoordinateRequestModel(-40.3, 4.2);

        List<EarthquakeResponseModel> result = underTest.execute(givenCoordinate);

        assertThat(result.get(0), is(new EarthquakeResponseModel(0.0, "aPlace", 2190)));
        assertThat(result.get(1), is(new EarthquakeResponseModel(0.0, "aPlace", 2230)));
    }

    @Test
    void nearbyTenEarthquakesOfEleven() {
        doReturn(asList(
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(60.0).withLon(-5.1).build()).build(),
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(-60.0).withLon(4.3).build()).build(),
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(-20.0).withLon(-10.3).build()).build(),
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(10.2).withLon(40.1).build()).build(),
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(-30.2).withLon(10.1).build()).build(),
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(30.2).withLon(40.1).build()).build(),
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(2.3).withLon(3.4).build()).build(),
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(34.8).withLon(-40.1).build()).build(),
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(90.0).withLon(-7.3).build()).build(),
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(30.2).withLon(4.1).build()).build(),
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(30.2).withLon(42.1).build()).build()
        )).when(usgsEarthquakePort).getLastThirtyDaysEarthquakes();

        NearbyEarthquakesCoordinateRequestModel givenCoordinate = new NearbyEarthquakesCoordinateRequestModel(-40.3, 4.2);

        List<EarthquakeResponseModel> result = underTest.execute(givenCoordinate);

        assertThat(result.size(), is(10));
        assertThat(result.get(0), is(new EarthquakeResponseModel(0.0, "aPlace", 1243)));
        assertThat(result.get(1), is(new EarthquakeResponseModel(0.0, "aPlace", 2190)));
        assertThat(result.get(2), is(new EarthquakeResponseModel(0.0, "aPlace", 2644)));
        assertThat(result.get(3), is(new EarthquakeResponseModel(0.0, "aPlace", 4737)));
        assertThat(result.get(4), is(new EarthquakeResponseModel(0.0, "aPlace", 6719)));
        assertThat(result.get(5), is(new EarthquakeResponseModel(0.0, "aPlace", 7839)));
        assertThat(result.get(6), is(new EarthquakeResponseModel(0.0, "aPlace", 8668)));
        assertThat(result.get(7), is(new EarthquakeResponseModel(0.0, "aPlace", 8758)));
        assertThat(result.get(8), is(new EarthquakeResponseModel(0.0, "aPlace", 9503)));
        assertThat(result.get(9), is(new EarthquakeResponseModel(0.0, "aPlace", 11185)));
    }
}