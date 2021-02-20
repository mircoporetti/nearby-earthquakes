package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.EarthCoordinate;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.entity.Earthquake;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.port.USGSEarthquakePort;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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
}