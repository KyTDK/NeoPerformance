package com.neomechanical.neoperformance.performance.modules.smartSchedule;

import com.neomechanical.neoconfig.neoutils.config.ConfigManager;
import com.neomechanical.neoperformance.NeoPerformance;

public class SmartSchedule {
    private final NeoPerformance plugin;
    private static ConfigManager configManager;

    public SmartSchedule(NeoPerformance plugin){
        this.plugin = plugin;
    }
    public void main() {
        configManager = new ConfigManager(plugin, "schedules.yml");

    }
    public static ConfigManager getConfigManager(){
        return configManager;
    }
}
