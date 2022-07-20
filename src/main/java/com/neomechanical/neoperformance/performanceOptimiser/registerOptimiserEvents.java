package com.neomechanical.neoperformance.performanceOptimiser;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.halt.HaltServer;
import com.neomechanical.neoperformance.performanceOptimiser.lagPrevention.LagPrevention;
import com.neomechanical.neoperformance.performanceOptimiser.performanceHeartBeat.HeartBeat;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.LagChecker;
import com.neomechanical.neoperformance.utils.Logger;
import org.bukkit.scheduler.BukkitRunnable;

public class registerOptimiserEvents {
    public static void register(NeoPerformance plugin) {
        //Register ability listeners
        new BukkitRunnable() {
            @Override
            public void run() {
                Logger.info("Server finished starting, registering performance optimisers");
                plugin.getServer().getPluginManager().registerEvents(new HaltServer(), plugin);
                plugin.getServer().getPluginManager().registerEvents(new LagPrevention(), plugin);
                new HeartBeat().start();
                new LagChecker().start();
            }
        }.runTaskLater(NeoPerformance.getInstance(), 1L);
    }
}
