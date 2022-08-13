package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.dataGetters;

import com.neomechanical.kyori.adventure.text.TextComponent;
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
        if (clusters.isEmpty() || clusters.get(0).size() < getLagNotifierData().getClusterSizeNotify()) {
            return null;
        }
        TextComponent.Builder builder = SmartScanNotifier.getChatData(player, 1, clusters);
        if (builder.children().isEmpty()) {
            return null;
        }
        return new LagData(player, "Entity Clusters", builder);
    }

    @Override
    public Integer getNotifySize() {
        return getLagNotifierData().getClusterSizeNotify();
    }
}
