package com.neomechanical.neoperformance.performanceOptimiser;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigCore;
import com.neomechanical.neoperformance.performanceOptimiser.halt.HaltServer;
import com.neomechanical.neoperformance.performanceOptimiser.lagPrevention.lagPrevention;
import com.neomechanical.neoperformance.performanceOptimiser.managers.TweakDataManager;
import com.neomechanical.neoperformance.performanceOptimiser.performanceHeartBeat.HeartBeat;
import com.neomechanical.neoperformance.utils.Logger;
import org.bukkit.scheduler.BukkitRunnable;

public class registerOptimiserEvents {
    public static void register(NeoPerformance plugin, TweakDataManager tweakDataManager) {
        PerformanceConfigCore config = new PerformanceConfigCore();
        config.createConfig();
        //Register ability listeners
        new BukkitRunnable() {
            @Override
            public void run() {
                Logger.info("Server finished starting, registering performance optimisers");
                plugin.getServer().getPluginManager().registerEvents(new HaltServer(), plugin);
                plugin.getServer().getPluginManager().registerEvents(new lagPrevention(), plugin);
                new HeartBeat().start();
            }
        }.runTaskLater(NeoPerformance.getInstance(), 1L);
    }
}
