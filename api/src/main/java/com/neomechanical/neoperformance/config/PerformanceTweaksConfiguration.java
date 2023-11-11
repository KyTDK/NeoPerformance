package com.neomechanical.neoperformance.config;

import com.neomechanical.neoconfig.neoutils.config.ConfigManager;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.managers.DataManager;

public class PerformanceTweaksConfiguration {
    private final NeoPerformance plugin;

    public PerformanceTweaksConfiguration(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    public void loadTweakSettings(DataManager dataManager) {
        dataManager.setConfig(new PerformanceConfig(new ConfigManager(plugin, "performanceConfig.yml").getConfig()));
    }
}
