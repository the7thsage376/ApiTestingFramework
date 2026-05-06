package common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            // This is okay, it means we'll use System properties on GitHub
        }
    }

    public static String getProperty(String key) {
        String systemValue = System.getProperty(key);
        return (systemValue != null) ? systemValue : properties.getProperty(key);
    }
}