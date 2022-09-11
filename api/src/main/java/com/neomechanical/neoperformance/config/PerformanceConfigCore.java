package com.neomechanical.neoperformance.config;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.utils.config.ConfigUpdater;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class PerformanceConfigCore implements ConfigFile {
    private final NeoPerformance plugin;
    private final File f;
    public FileConfiguration config;

    public PerformanceConfigCore(NeoPerformance plugin) {
        this.plugin = plugin;
        f = new File(plugin.getDataFolder(), "performanceConfig.yml");
        config = YamlConfiguration.loadConfiguration(f);
    }

    public FileConfiguration createConfig() {
        if (!f.exists()) {
            plugin.saveResource(getName(), false);
        }
//The config needs to exist before using the updater
        try {
            ConfigUpdater.update(plugin, "performanceConfig.yml", f, Collections.singletonList(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        config = YamlConfiguration.loadConfiguration(f);
        return config;
    }
}
