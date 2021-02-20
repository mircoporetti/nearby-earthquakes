package me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase;

import me.mircoporetti.nearbyearthquakes.domain.earthquake.port.USGSEarthquakePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;

class NearbyEarthquakesUseCaseTest {
    @Mock
    USGSEarthquakePort usgsEarthquakePort;

    private NearbyEarthquakes underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new NearbyEarthquakes();
    }

    @Test
    void notValidCoordinate() {
        NearbyEarthquakesCoordinateRequestModel givenCoordinate = new NearbyEarthquakesCoordinateRequestModel(-91, 181);

        assertThrows(NotAnEarthCoordinateException.class, () -> underTest.execute(givenCoordinate));
    }

}