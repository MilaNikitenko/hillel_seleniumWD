package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFactory {

    private static final Properties properties = new Properties();

    public static Properties loadProperties(String fileName) {
        try (InputStream input = PropertyFactory.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find " + fileName);
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Error loading properties file: " + fileName, ex);
        }
        return properties;
    }

    public static String getLoginPageLinkProperty() {
        return properties.getProperty("loginPageLink");
    }

    public static String getUserEmailProperty() {
        return properties.getProperty("user.email");
    }

    public static String getUserPasswordProperty() {
        return properties.getProperty("user.password");
    }
}
