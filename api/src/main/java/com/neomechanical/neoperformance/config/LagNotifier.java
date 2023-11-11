package com.neomechanical.neoperformance.config;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;

@Data
public class LagNotifier {

    private int lagNotifierRunInterval;
    private int entitiesInChunkNotify;
    private int clusterSizeNotify;
    public LagNotifier(FileConfiguration fileConfiguration) {
        this.lagNotifierRunInterval = fileConfiguration.getInt("lag_notifier.runInterval");
        this.entitiesInChunkNotify = fileConfiguration.getInt("lag_notifier.entitiesInChunkNotify");
        this.clusterSizeNotify = fileConfiguration.getInt("lag_notifier.clusterSizeNotify");
    }
}
