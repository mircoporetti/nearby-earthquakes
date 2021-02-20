package me.mircoporetti.nearbyearthquakes;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.port.USGSEarthquakePort;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase.NearbyEarthquakes;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase.NearbyEarthquakesUseCase;
import me.mircoporetti.nearbyearthquakes.presentation.earthquake.CoordinateMessageRequest;
import me.mircoporetti.nearbyearthquakes.presentation.earthquake.EarthquakePresenter;
import me.mircoporetti.nearbyearthquakes.usgs_adapter.earthquake.RestClient;
import me.mircoporetti.nearbyearthquakes.usgs_adapter.earthquake.USGSEarthquakeRestAdapter;
import me.mircoporetti.nearbyearthquakes.usgs_adapter.earthquake.USGSEarthquakeRestClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Application {

    public static void main(String[] args) throws IOException {
        EarthquakePresenter earthquakePresenter = initializeApplicationComponents();

        System.out.println("Write a latitude value");
        String lat = readFromStandardInput();
        System.out.println("Write a longitude value");
        String lon = readFromStandardInput();
        System.out.println("\nWaiting for nearby earthquakes' info... \n");

        List<String> nearbyEarthquakesResult = earthquakePresenter.getNearbyEarthquakes(new CoordinateMessageRequest(lat, lon));

        nearbyEarthquakesResult.forEach(System.out::println);
    }

    private static EarthquakePresenter initializeApplicationComponents() throws IOException {
        NearbyEarthquakesProperty properties = new NearbyEarthquakesProperty();
        properties.getProperties();

        String usgsUrl = properties.getProperties().getProperty("usgsUrl");
        ObjectMapper objectMapper = new ObjectMapper();
        RestClient restClient = new USGSEarthquakeRestClient(objectMapper, usgsUrl);
        USGSEarthquakePort usgsEarthquakePort = new USGSEarthquakeRestAdapter(restClient);
        NearbyEarthquakesUseCase nearbyEarthquakesUseCase = new NearbyEarthquakes(usgsEarthquakePort);
        return new EarthquakePresenter(nearbyEarthquakesUseCase);
    }

    public static String readFromStandardInput() {
        String line = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
}
