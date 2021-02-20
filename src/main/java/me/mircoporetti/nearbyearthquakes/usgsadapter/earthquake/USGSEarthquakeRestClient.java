package me.mircoporetti.nearbyearthquakes.usgsadapter.earthquake;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class USGSEarthquakeRestClient implements RestClient {

    @Override
    public USGSResponse findLastThirtyDaysEarthquakes() {

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson"))
                .build();
        try {
            HttpResponse<String> jsonResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse.body(), USGSResponse.class);
        } catch (Exception e) {
            throw new UsgsRestClientException("Error during rest call to USGS", e);
        }
    }
}
