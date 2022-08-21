package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.managers.LagDataManager;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.report.LagReport;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.report.LagReportBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class LagChecker {
    private final NeoPerformance plugin;

    public LagChecker(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                //Generate data
                LagDataManager lagDataManager = new LagDataManager(plugin);
                lagDataManager.generateAll();
                List<? extends Player> recipients = Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("neoperformance.smartnotify") || p.isOp()).toList();
                for (Player recipient : recipients) {
                    LagReportBuilder lagReportBuilder = new LagReport().reportBuilder();
                    lagReportBuilder.addData(lagDataManager.getAllLagData(recipient));
                    lagReportBuilder.sendReport(recipient);
                }
            }
        }.runTaskTimer(NeoPerformance.getInstance(), 0, 20L * plugin.getDataManager().getLagNotifierData().getRunInterval());
    }
}
