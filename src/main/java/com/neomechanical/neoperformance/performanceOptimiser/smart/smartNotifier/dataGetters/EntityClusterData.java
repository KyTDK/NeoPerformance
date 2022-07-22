package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.dataGetters;

import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartClear.SmartScan;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartClear.SmartScanNotifier;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.DataGetter;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.managers.LagData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EntityClusterData extends DataGetter implements PerformanceConfigurationSettings {
    private static List<List<Entity>> clusters = new ArrayList<>();


    @Override
    public void generate() {
        clusters = SmartScan.scan(10, getLagNotifierData().getClusterSizeNotify(), getCommandData(), Bukkit.getWorlds().toArray(World[]::new));
    }

    @Override
    public LagData get(Player player) {
        return new LagData(player, "Entity Clusters", SmartScanNotifier.getChatData(player, 1, clusters));
    }

    @Override
    public Integer getNotifySize() {
        return getLagNotifierData().getClusterSizeNotify();
    }
}
