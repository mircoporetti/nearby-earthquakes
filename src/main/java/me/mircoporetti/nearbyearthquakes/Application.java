package me.mircoporetti.nearbyearthquakes;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.port.USGSEarthquakePort;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase.NearbyEarthquakes;
import me.mircoporetti.nearbyearthquakes.domain.earthquake.usecase.NearbyEarthquakesUseCase;
import me.mircoporetti.nearbyearthquakes.presentation.earthquake.CoordinateMessageRequest;
import me.mircoporetti.nearbyearthquakes.presentation.earthquake.EarthquakePresenter;
import me.mircoporetti.nearbyearthquakes.usgsadapter.earthquake.RestClient;
import me.mircoporetti.nearbyearthquakes.usgsadapter.earthquake.USGSEarthquakeRestAdapter;
import me.mircoporetti.nearbyearthquakes.usgsadapter.earthquake.USGSEarthquakeRestClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Application {

    public static void main(String[] args) {

        String usgsUrl = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";
        ObjectMapper objectMapper = new ObjectMapper();
        RestClient restClient = new USGSEarthquakeRestClient(objectMapper, usgsUrl);
        USGSEarthquakePort usgsEarthquakePort = new USGSEarthquakeRestAdapter(restClient);
        NearbyEarthquakesUseCase nearbyEarthquakesUseCase = new NearbyEarthquakes(usgsEarthquakePort);
        EarthquakePresenter earthquakePresenter = new EarthquakePresenter(nearbyEarthquakesUseCase);

        System.out.println("Write a latitude value");
        String lat = read();
        System.out.println("Write a longitude value");
        String lon = read();

        System.out.println("\nWaiting for nearby earthquakes... \n");

        List<String> nearbyEarthquakesResult = earthquakePresenter.getNearbyEarthquakes(new CoordinateMessageRequest(lat, lon));
        nearbyEarthquakesResult.forEach(System.out::println);
    }

    public static String read() {
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
