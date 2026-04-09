package com.neomechanical.neoperformance.performance.modules.insight.utils;

import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class ServerConfiguration {
    private static final String DEFAULT_VALUE = "0";

    private ServerConfiguration() {
    }

    public enum ServerProperty {
        VIEW_DISTANCE("view-distance"),
        SNOOPER("snooper-enabled"),
        PACKET_COMPRESSION_LIMIT("network-compression-threshold");

        private final String key;

        ServerProperty(String key) {
            this.key = key;
        }
    }

    public static String getServerProperty(ServerProperty property) {
        Properties properties = loadProperties();
        return properties.getProperty(property.key, DEFAULT_VALUE);
    }

    public static void setServerProperty(ServerProperty property, String value) {
        Properties properties = loadProperties();
        properties.setProperty(property.key, value);
        saveProperties(properties);
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        File file = new File(Bukkit.getWorldContainer(), "server.properties");
        if (!file.exists()) {
            return properties;
        }
        try (FileInputStream inputStream = new FileInputStream(file)) {
            properties.load(inputStream);
        } catch (IOException ignored) {
            // Keep defaults if the file cannot be read.
        }
        return properties;
    }

    private static void saveProperties(Properties properties) {
        File file = new File(Bukkit.getWorldContainer(), "server.properties");
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            properties.store(outputStream, "Updated by NeoPerformance");
        } catch (IOException ignored) {
            // Ignore write failures to avoid crashing command flow.
        }
    }
}
