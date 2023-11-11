package com.neomechanical.neoperformance.config;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

@Data
public class PerformanceConfig {
    private FileConfiguration fileConfiguration;
    //Performance tweak settings
    private PerformanceTweakSettings performanceTweakSettings;
    // Halt settings
    private HaltSettings haltSettings;
    // Lag Notifier
    private LagNotifier lagNotifier;
    // Halt Actions
    private List<String> haltActions;
    // Commands
    private Commands commands;
    // Email Notifications
    private EmailNotifications emailNotifications;
    // Visual
    private Visual visual;

    public PerformanceConfig(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;

        // Initialize other fields here after setting fileConfiguration
        this.performanceTweakSettings = new PerformanceTweakSettings(fileConfiguration);
        this.haltSettings = new HaltSettings(fileConfiguration);
        this.lagNotifier = new LagNotifier(fileConfiguration);
        this.haltActions = fileConfiguration.getStringList("halt_actions");
        this.commands = new Commands(fileConfiguration);
        this.emailNotifications = new EmailNotifications(fileConfiguration);
        this.visual = new Visual(fileConfiguration);
    }
}
