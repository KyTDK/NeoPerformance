package com.neomechanical.neoperformance.config;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;

@Data
public class Visual {
    private String language;
    private boolean showPluginUpdateInMain;

    public Visual(FileConfiguration fileConfiguration) {
        // Initialize fields inside the constructor
        this.language = fileConfiguration.getString("visual.language");
        this.showPluginUpdateInMain = fileConfiguration.getBoolean("visual.showPluginUpdateInMain");
    }
}
