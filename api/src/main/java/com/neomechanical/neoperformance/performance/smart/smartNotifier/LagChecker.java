package com.neomechanical.neoperformance.performance.smart.smartNotifier;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.smart.smartNotifier.managers.LagDataManager;
import com.neomechanical.neoperformance.performance.smart.smartNotifier.report.LagReport;
import com.neomechanical.neoperformance.performance.smart.smartNotifier.report.LagReportBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

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
                List<? extends Player> recipients = Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("neoperformance.smartnotify") || p.isOp()).collect(Collectors.toList());
                for (Player recipient : recipients) {
                    LagReportBuilder lagReportBuilder = new LagReport().reportBuilder();
                    lagReportBuilder.addData(lagDataManager.getAllLagData(recipient));
                    lagReportBuilder.sendReport(recipient);
                }
            }
        }.runTaskTimer(plugin, 0, 20L * plugin.getDataManager().getPerformanceConfig().getLagNotifier().getLagNotifierRunInterval());
    }
}
