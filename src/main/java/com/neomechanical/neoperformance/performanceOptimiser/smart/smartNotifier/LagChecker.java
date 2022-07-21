package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class LagChecker implements PerformanceConfigurationSettings {
    private final NeoPerformance plugin = NeoPerformance.getInstance();

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                //Get data
                List<? extends Player> recipients = Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("neoperformance.smartnotify") || p.isOp()).toList();
                if (getLagNotifierData().getClusterSizeNotify() > 1) {
                    LagNotifier.chunkChecker();
                }
                if (getLagNotifierData().getClusterSizeNotify() > 1) {
                    LagNotifier.entityChecker(getCommandData(), getLagNotifierData().getClusterSizeNotify());
                }
            }
        }.runTaskTimer(NeoPerformance.getInstance(), 0, 20L * getLagNotifierData().getRunInterval());
    }
}
