package ru.sfedu.lab1;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtils {
    private static String defaultConfigPath = "src/main/resources/Environment.properties";
    private static final Properties configuration = new Properties();

    public ConfigUtils(){
        String str = System.getProperty("path");
        if (str != null){
            defaultConfigPath=str;
        }
    }

    private static Properties getConfiguration() throws IOException {
        if (configuration.isEmpty()) {
            loadConfiguration();
        }
        return configuration;
    }

    private static void loadConfiguration() throws IOException {
        FileInputStream in = new FileInputStream(defaultConfigPath);
        try {
            configuration.load(in);
        } catch (IOException ex) {
            throw new IOException(ex);
        } finally {
            in.close();
        }
    }

    public static String getConfigurationEntry(String key) throws IOException {
        return getConfiguration().getProperty(key);
    }
}
