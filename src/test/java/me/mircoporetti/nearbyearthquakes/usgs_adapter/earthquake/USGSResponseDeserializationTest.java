package me.mircoporetti.nearbyearthquakes.usgs_adapter.earthquake;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class USGSResponseDeserializationTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getLastThirtyDaysEarthquakes() throws JsonProcessingException {

        USGSResponse result = objectMapper.readValue(givenJson(), USGSResponse.class);
        Double lonFirstEarthquake = result.features.get(0).geometry.coordinates.get(0);
        Double latFirstEarthquake = result.features.get(0).geometry.coordinates.get(1);

        assertThat(result.features.size(), is(2));
        assertThat(lonFirstEarthquake, is(-155.408996582031));
        assertThat(latFirstEarthquake, is(19.2110004425049));

    }

    private String givenJson() {
        return "{\n" +
                "    \"type\": \"FeatureCollection\",\n" +
                "    \"metadata\": {\n" +
                "        \"generated\": 1613839933000,\n" +
                "        \"url\": \"https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson\",\n" +
                "        \"title\": \"USGS All Earthquakes, Past Month\",\n" +
                "        \"status\": 200,\n" +
                "        \"api\": \"1.10.3\",\n" +
                "        \"count\": 10813\n" +
                "    },\n" +
                "    \"features\": [\n" +
                "        {\n" +
                "            \"type\": \"Feature\",\n" +
                "            \"properties\": {\n" +
                "                \"mag\": 2.4,\n" +
                "                \"place\": \"7 km E of Pāhala, Hawaii\",\n" +
                "                \"time\": 1613837709890,\n" +
                "                \"updated\": 1613838039940,\n" +
                "                \"tz\": null,\n" +
                "                \"url\": \"https://earthquake.usgs.gov/earthquakes/eventpage/hv72358917\",\n" +
                "                \"detail\": \"https://earthquake.usgs.gov/earthquakes/feed/v1.0/detail/hv72358917.geojson\",\n" +
                "                \"felt\": null,\n" +
                "                \"cdi\": null,\n" +
                "                \"mmi\": null,\n" +
                "                \"alert\": null,\n" +
                "                \"status\": \"automatic\",\n" +
                "                \"tsunami\": 0,\n" +
                "                \"sig\": 89,\n" +
                "                \"net\": \"hv\",\n" +
                "                \"code\": \"72358917\",\n" +
                "                \"ids\": \",hv72358917,\",\n" +
                "                \"sources\": \",hv,\",\n" +
                "                \"types\": \",origin,phase-data,\",\n" +
                "                \"nst\": 50,\n" +
                "                \"dmin\": null,\n" +
                "                \"rms\": 0.180000007,\n" +
                "                \"gap\": 149,\n" +
                "                \"magType\": \"ml\",\n" +
                "                \"type\": \"earthquake\",\n" +
                "                \"title\": \"M 2.4 - 7 km E of Pāhala, Hawaii\"\n" +
                "            },\n" +
                "            \"geometry\": {\n" +
                "                \"type\": \"Point\",\n" +
                "                \"coordinates\": [\n" +
                "                    -155.408996582031,\n" +
                "                    19.2110004425049,\n" +
                "                    33.7400016784668\n" +
                "                ]\n" +
                "            },\n" +
                "            \"id\": \"hv72358917\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"Feature\",\n" +
                "            \"properties\": {\n" +
                "                \"mag\": 1.5,\n" +
                "                \"place\": \"44 km ESE of Pedro Bay, Alaska\",\n" +
                "                \"time\": 1613837406157,\n" +
                "                \"updated\": 1613837626960,\n" +
                "                \"tz\": null,\n" +
                "                \"url\": \"https://earthquake.usgs.gov/earthquakes/eventpage/ak0212clzshz\",\n" +
                "                \"detail\": \"https://earthquake.usgs.gov/earthquakes/feed/v1.0/detail/ak0212clzshz.geojson\",\n" +
                "                \"felt\": null,\n" +
                "                \"cdi\": null,\n" +
                "                \"mmi\": null,\n" +
                "                \"alert\": null,\n" +
                "                \"status\": \"automatic\",\n" +
                "                \"tsunami\": 0,\n" +
                "                \"sig\": 35,\n" +
                "                \"net\": \"ak\",\n" +
                "                \"code\": \"0212clzshz\",\n" +
                "                \"ids\": \",ak0212clzshz,\",\n" +
                "                \"sources\": \",ak,\",\n" +
                "                \"types\": \",origin,\",\n" +
                "                \"nst\": null,\n" +
                "                \"dmin\": null,\n" +
                "                \"rms\": 0.46,\n" +
                "                \"gap\": null,\n" +
                "                \"magType\": \"ml\",\n" +
                "                \"type\": \"earthquake\",\n" +
                "                \"title\": \"M 1.5 - 44 km ESE of Pedro Bay, Alaska\"\n" +
                "            },\n" +
                "            \"geometry\": {\n" +
                "                \"type\": \"Point\",\n" +
                "                \"coordinates\": [\n" +
                "                    -153.353,\n" +
                "                    59.6562,\n" +
                "                    108.4\n" +
                "                ]\n" +
                "            },\n" +
                "            \"id\": \"ak0212clzshz\"\n" +
                "        }],\n" +
                "\"bbox\": [\n" +
                "        -179.9899,\n" +
                "        -64.7563,\n" +
                "        -4.7,\n" +
                "        179.6491,\n" +
                "        85.0818,\n" +
                "        662.1\n" +
                "    ]\n" +
                "}";
    }
}
