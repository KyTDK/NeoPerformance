package com.neomechanical.neoperformance.config;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;

@Data
public class PerformanceTweakSettings {

    private int tpsHaltAt;
    private boolean notifyAdmin;
    private boolean broadcastHalt;
    private boolean notifyDiscord;
    private int mobCap;
    private int mobCapRadius;
    private int explosionCap;
    private int heartBeatRate;
    public PerformanceTweakSettings(FileConfiguration fileConfiguration) {
        this.tpsHaltAt = fileConfiguration.getInt("performance_tweak_settings.tpsHaltAt");
        this.notifyAdmin = fileConfiguration.getBoolean("performance_tweak_settings.notifyAdmin");
        this.broadcastHalt = fileConfiguration.getBoolean("performance_tweak_settings.broadcastHalt");
        this.notifyDiscord = fileConfiguration.getBoolean("performance_tweak_settings.notifyDiscord");
        this.mobCap = fileConfiguration.getInt("performance_tweak_settings.mobCap");
        this.mobCapRadius = fileConfiguration.getInt("performance_tweak_settings.mobCapRadius");
        this.explosionCap = fileConfiguration.getInt("performance_tweak_settings.explosionCap");
        this.heartBeatRate = fileConfiguration.getInt("performance_tweak_settings.heartBeatRate");
    }
}
