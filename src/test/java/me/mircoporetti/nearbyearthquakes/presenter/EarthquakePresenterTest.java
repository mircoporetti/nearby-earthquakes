package me.mircoporetti.nearbyearthquakes.presenter;

import me.mircoporetti.nearbyearthquakes.domain.EarthquakeResponseModel;
import me.mircoporetti.nearbyearthquakes.domain.NearbyEarthquakesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
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
    void retrieveNearbyEarthquakes() {

        EarthquakeResponseModel earthquakeResponseModel = new EarthquakeResponseModel(1.3, "Somewhere", 100);
        List<EarthquakeResponseModel> expected = Collections.singletonList(earthquakeResponseModel);

        doReturn(expected).when(nearbyEarthquakesUseCase).execute(any());

        List<String> result = underTest.getNearbyEarthquakes(0.000000, 0.000000);

        assertThat(result, is(Collections.singletonList("M 1.3 - Somewhere || 100")));
    }
}