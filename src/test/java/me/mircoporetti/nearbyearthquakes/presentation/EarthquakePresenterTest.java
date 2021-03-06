package me.mircoporetti.nearbyearthquakes.presentation;

import me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase.NearbyEarthquakesCoordinateRequestModel;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase.NearbyEarthquakeResponseModel;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase.NearbyEarthquakesUseCase;
import me.mircoporetti.nearbyearthquakes.presentation.earthquake.CoordinateFormatException;
import me.mircoporetti.nearbyearthquakes.presentation.earthquake.CoordinateMessageRequest;
import me.mircoporetti.nearbyearthquakes.presentation.earthquake.EarthquakePresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class EarthquakePresenterTest {

    @Mock
    private NearbyEarthquakesUseCase nearbyEarthquakesUseCase;

    private EarthquakePresenter underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new EarthquakePresenter(nearbyEarthquakesUseCase);
    }

    @Test
    void getNearbyEarthquakes() {

        NearbyEarthquakeResponseModel nearbyEarthquakeResponseModel = new NearbyEarthquakeResponseModel(1.3, "Somewhere", 100);
        List<NearbyEarthquakeResponseModel> expected = Collections.singletonList(nearbyEarthquakeResponseModel);

        doReturn(expected).when(nearbyEarthquakesUseCase).execute(new NearbyEarthquakesCoordinateRequestModel(0.324324, 45.3224));

        List<String> result = underTest.getNearbyEarthquakes(new CoordinateMessageRequest("0.324324", "45.3224"));

        assertThat(result, is(Collections.singletonList("M 1.3 - Somewhere || 100")));
    }

    @Test
    void notValidLat() {

        assertThrows(CoordinateFormatException.class, () -> underTest.getNearbyEarthquakes(new CoordinateMessageRequest("aLat", "0.000000")));

        verify(nearbyEarthquakesUseCase, never()).execute(any());
    }

    @Test
    void notValidLon() {

        assertThrows(CoordinateFormatException.class, () -> underTest.getNearbyEarthquakes(new CoordinateMessageRequest("0.000000", "aLon")));

        verify(nearbyEarthquakesUseCase, never()).execute(any());
    }
}