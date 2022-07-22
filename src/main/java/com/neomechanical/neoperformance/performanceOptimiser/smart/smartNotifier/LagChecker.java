package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.managers.LagDataManager;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.report.LagReport;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.report.LagReportBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class LagChecker implements PerformanceConfigurationSettings {

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                //Generate data
                LagDataManager lagDataManager = new LagDataManager();
                lagDataManager.generateAll();
                List<? extends Player> recipients = Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("neoperformance.smartnotify") || p.isOp()).toList();
                for (Player recipient : recipients) {
                    LagReportBuilder lagReportBuilder = new LagReport().reportBuilder();
                    lagReportBuilder.addData(lagDataManager.getAllLagData(recipient));
                    lagReportBuilder.sendReport(recipient);
                }
            }
        }.runTaskTimer(NeoPerformance.getInstance(), 0, 20L * getLagNotifierData().getRunInterval());
    }
}
