package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

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
        assertThat(result, Matchers.is(emptyList()));
    }

}