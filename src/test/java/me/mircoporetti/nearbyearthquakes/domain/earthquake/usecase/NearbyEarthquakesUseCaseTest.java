package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

import me.mircoporetti.nearbyearthquakes.domain.earthquake.port.USGSEarthquakePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.EarthCoordinateBuilder.anEarthCoordinate;
import static me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.EarthquakeBuilder.anEarthquake;
import static me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase.EarthquakeResponseModelBuilder.anEarthquakeResponseModel;
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

        List<NearbyEarthquakeResponseModel> result = underTest.execute(givenCoordinate);

        verify(usgsEarthquakePort).getLastThirtyDaysEarthquakes();
        assertThat(result, is(emptyList()));
    }

    @Test
    void anEarthquakeWithZeroDistance() {

        doReturn(singletonList(
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(10.0).withLon(10.0).build()).withMagnitude(4).withPlace("Somewhere").build()
        )).when(usgsEarthquakePort).getLastThirtyDaysEarthquakes();

        NearbyEarthquakesCoordinateRequestModel givenCoordinate = new NearbyEarthquakesCoordinateRequestModel(10.0, 10.0);

        List<NearbyEarthquakeResponseModel> result = underTest.execute(givenCoordinate);

        assertThat(result, is(singletonList(new NearbyEarthquakeResponseModel(4, "Somewhere", 0))));
    }

    @Test
    void anEarthquakeWithNonZeroDistance() {
        doReturn(singletonList(
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(-60.0).withLon(10.3).build()).build()
        )).when(usgsEarthquakePort).getLastThirtyDaysEarthquakes();

        NearbyEarthquakesCoordinateRequestModel givenCoordinate = new NearbyEarthquakesCoordinateRequestModel(-40.3, 4.2);

        List<NearbyEarthquakeResponseModel> result = underTest.execute(givenCoordinate);

        assertThat(result, is(singletonList(anEarthquakeResponseModel().withDistance(2230).build())));
    }

    @Test
    void twoEarthquakesWithDifferentCoordinate() {
        doReturn(asList(
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(-60.0).withLon(10.3).build()).withMagnitude(3).withPlace("Somewhere").build(),
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(-60.0).withLon(4.3).build()).withMagnitude(4).withPlace("Somewhere else").build()
        )).when(usgsEarthquakePort).getLastThirtyDaysEarthquakes();

        NearbyEarthquakesCoordinateRequestModel givenCoordinate = new NearbyEarthquakesCoordinateRequestModel(-40.3, 4.2);

        List<NearbyEarthquakeResponseModel> result = underTest.execute(givenCoordinate);

        assertThat(result.get(0), is(anEarthquakeResponseModel().withDistance(2190).withMagnitude(4).withPlace("Somewhere else").build()));
        assertThat(result.get(1), is(anEarthquakeResponseModel().withDistance(2230).withMagnitude(3).withPlace("Somewhere").build()));
    }

    @Test
    void twoEarthquakesWithSameCoordinate() {
        doReturn(asList(
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(-60.0).withLon(10.3).build()).withMagnitude(3).withPlace("Somewhere").build(),
                anEarthquake().withCoordinate(anEarthCoordinate().withLat(-60.0).withLon(10.3).build()).withMagnitude(4).withPlace("Somewhere else").build()
        )).when(usgsEarthquakePort).getLastThirtyDaysEarthquakes();

        NearbyEarthquakesCoordinateRequestModel givenCoordinate = new NearbyEarthquakesCoordinateRequestModel(-40.3, 4.2);

        List<NearbyEarthquakeResponseModel> result = underTest.execute(givenCoordinate);

        assertThat(result.size(), is(1));
    }

    @Test
    void firstTenNearbyEarthquakesOfEleven() {
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

        List<NearbyEarthquakeResponseModel> result = underTest.execute(givenCoordinate);

        assertThat(result.size(), is(10));
        assertThat(result.get(0), is(anEarthquakeResponseModel().withDistance(1243).build()));
        assertThat(result.get(1), is(anEarthquakeResponseModel().withDistance(2190).build()));
        assertThat(result.get(2), is(anEarthquakeResponseModel().withDistance(2644).build()));
        assertThat(result.get(3), is(anEarthquakeResponseModel().withDistance(4737).build()));
        assertThat(result.get(4), is(anEarthquakeResponseModel().withDistance(6719).build()));
        assertThat(result.get(5), is(anEarthquakeResponseModel().withDistance(7839).build()));
        assertThat(result.get(6), is(anEarthquakeResponseModel().withDistance(8668).build()));
        assertThat(result.get(7), is(anEarthquakeResponseModel().withDistance(8758).build()));
        assertThat(result.get(8), is(anEarthquakeResponseModel().withDistance(9503).build()));
        assertThat(result.get(9), is(anEarthquakeResponseModel().withDistance(11185).build()));
    }
}