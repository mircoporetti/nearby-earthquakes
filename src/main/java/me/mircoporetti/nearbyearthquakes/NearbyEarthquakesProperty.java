package me.mircoporetti.nearbyearthquakes;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class NearbyEarthquakesProperty {
    private InputStream inputStream;

    public Properties getProperties() throws IOException {

        Properties properties = new Properties();
        String propFileName = "config.properties";
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return properties;
    }
}
