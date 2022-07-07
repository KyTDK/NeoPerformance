package com.neomechanical.neoperformance.performanceOptimiser.config;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.utils.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class PerformanceConfigCore implements ConfigFile {
    private static File f = new File(NeoPerformance.getInstance().getDataFolder().getAbsolutePath() + File.separator + "performanceConfig.yml");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(f);
    public void createConfig() {
        if (!f.exists()) {
            try {
                if (!f.createNewFile()) {
                    Logger.warn("Could not create performanceConfig.yml");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        NeoPerformance.getInstance().saveResource(getName(), false);
        f = new File(NeoPerformance.getInstance().getDataFolder().getAbsolutePath() + File.separator + getName());
        config = YamlConfiguration.loadConfiguration(f);
    }
    public static void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(f);
    }
}
