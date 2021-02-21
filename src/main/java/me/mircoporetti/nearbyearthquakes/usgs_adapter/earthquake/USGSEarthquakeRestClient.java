package me.mircoporetti.nearbyearthquakes.usgs_adapter.earthquake;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/*
    The Http call is quite slow. It could be improved for example with calling it
    at the startup of the application or/and caching it
*/
public class USGSEarthquakeRestClient implements RestClient {

    private final ObjectMapper objectMapper;
    private final String usgsUrl;

    public USGSEarthquakeRestClient(ObjectMapper objectMapper, String usgsUrl) {
        this.objectMapper = objectMapper;
        this.usgsUrl = usgsUrl;
    }

    @Override
    public USGSResponse findLastThirtyDaysEarthquakes() {

        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .connectTimeout(Duration.ofSeconds(20))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(usgsUrl))
                    .build();
            HttpResponse<String> jsonResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(jsonResponse.body(), USGSResponse.class);
        } catch (Exception e) {
            throw new UsgsRestClientException("Error during http call to USGS", e);
        }
    }
}
