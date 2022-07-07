package com.neomechanical.neoperformance.performanceOptimiser.managers;

import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigCore;
import org.bukkit.configuration.file.FileConfiguration;

public class performanceTweaksConfiguration {
    FileConfiguration config = PerformanceConfigCore.config;
    String settings = "performance_tweak_settings.";
    public TweakData loadTweakSettings() {
        int tpsHaltAt = 0;
        boolean notifyAdmin = false;
            if (config.getString(settings+"tpsHaltAt") != null) {
                tpsHaltAt = config.getInt(settings+"tpsHaltAt");
            }
            if (config.getString(settings+"notifyAdmin") != null) {
                notifyAdmin = config.getBoolean(settings+"notifyAdmin");
            }
        return new TweakData(tpsHaltAt, notifyAdmin);
    }
}
