package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.utils.messages.MessageUtil;
import com.neomechanical.neoperformance.utils.messages.Messages;
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
                for (Player recipient : recipients) {
                    MessageUtil.send(recipient, Messages.MAIN_LAG_REPORT_PREFIX);
                    MessageUtil.sendMM(recipient, message);
                    MessageUtil.send(recipient, Messages.MAIN_SUFFIX);
                }
                if (getLagNotifierData().getClusterSizeNotify() > 1) {
                    LagNotifier.getChunkData();
                }
                if (getLagNotifierData().getClusterSizeNotify() > 1) {
                    LagNotifier.getClusterData(getCommandData(), getLagNotifierData().getClusterSizeNotify());
                }
            }
        }.runTaskTimer(NeoPerformance.getInstance(), 0, 20L * getLagNotifierData().getRunInterval());
    }
}
