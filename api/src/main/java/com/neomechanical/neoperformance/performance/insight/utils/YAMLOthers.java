package com.neomechanical.neoperformance.performance.insight.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class YAMLOthers {
    private final File file;

    public YAMLOthers(String config) {
        this.file = new File(config);
    }

    public YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(file);
    }

    public boolean configExists() {
        return file.exists();
    }
}
