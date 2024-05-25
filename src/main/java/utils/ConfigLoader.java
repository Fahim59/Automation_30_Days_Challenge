package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    private Properties properties;
    private static ConfigLoader configLoader;

    /**
     * This method is used to load the properties from config.properties file
     * @return it returns Properties prop object
     */

    public Properties initializeProperty() {
        properties = new Properties();
        try {
            FileInputStream ip = new FileInputStream("src/main/resources/config.properties");
            properties.load(ip);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
