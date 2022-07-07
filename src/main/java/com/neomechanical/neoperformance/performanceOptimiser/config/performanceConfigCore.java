package com.neomechanical.neoperformance.performanceOptimiser.config;

import com.neomechanical.neoperformance.NeoPerformance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class performanceConfigCore implements ConfigFile {
    private static File f = new File(NeoPerformance.getInstance().getDataFolder().getAbsolutePath() + File.separator + "performanceConfig.yml");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(f);
    public void createConfig() {
        NeoPerformance.getInstance().saveResource(getName(), false);
        f = new File(NeoPerformance.getInstance().getDataFolder().getAbsolutePath() + File.separator + getName());
        config = YamlConfiguration.loadConfiguration(f);
    }
}
