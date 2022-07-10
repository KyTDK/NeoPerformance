package com.neomechanical.neoperformance.performanceOptimiser.config;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.utils.config.ConfigUpdater;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PerformanceConfigCore implements ConfigFile {
    private static final NeoPerformance plugin = NeoPerformance.getInstance();
    private static final File f = new File(plugin.getDataFolder(),"performanceConfig.yml");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(f);
    public FileConfiguration createConfig() {
        if (!f.exists()) {
            plugin.saveResource(getName(), false);
        }
//The config needs to exist before using the updater
        try {
            ConfigUpdater.update(plugin, "performanceConfig.yml", f, List.of(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        config = YamlConfiguration.loadConfiguration(f);
        return config;
    }
}
