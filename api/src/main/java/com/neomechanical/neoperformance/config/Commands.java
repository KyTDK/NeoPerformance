package com.neomechanical.neoperformance.config;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

@Data
public class Commands {

    private int defaultClusterSize;
    private List<String> smartClearExcludeEntities;
    public Commands(FileConfiguration fileConfiguration) {
        this.defaultClusterSize = fileConfiguration.getInt("commands.defaultClusterSize");
        this.smartClearExcludeEntities = fileConfiguration.getStringList("commands.smartClearExcludeEntities");
    }
}
