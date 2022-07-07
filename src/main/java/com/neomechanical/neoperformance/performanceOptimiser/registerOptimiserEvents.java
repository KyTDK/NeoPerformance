package com.neomechanical.neoperformance.performanceOptimiser;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.config.performanceConfigCore;
import com.neomechanical.neoperformance.performanceOptimiser.halt.HaltServer;
import com.neomechanical.neoperformance.performanceOptimiser.lagPrevention.lagPrevention;
import com.neomechanical.neoperformance.performanceOptimiser.managers.TweakData;
import com.neomechanical.neoperformance.performanceOptimiser.managers.TweakDataManager;
import com.neomechanical.neoperformance.performanceOptimiser.managers.performanceTweaksConfiguration;
import com.neomechanical.neoperformance.performanceOptimiser.performanceHeartBeat.HeartBeat;
import com.neomechanical.neoperformance.utils.Logger;
import org.bukkit.scheduler.BukkitRunnable;

public class registerOptimiserEvents {
    public static void register(NeoPerformance plugin) {
        performanceConfigCore config = new performanceConfigCore();
        config.createConfig();
        TweakData tweakData = new performanceTweaksConfiguration().loadTweakSettings();
        TweakDataManager tweakDataManager = new TweakDataManager(tweakData);
        //Register ability listeners
        new BukkitRunnable() {
            @Override
            public void run() {
                Logger.info("Server finished starting, registering performance optimisers");
                plugin.getServer().getPluginManager().registerEvents(new HaltServer(tweakDataManager), plugin);
                plugin.getServer().getPluginManager().registerEvents(new lagPrevention(), plugin);
                new HeartBeat().start(tweakDataManager);
            }
        }.runTaskLater(NeoPerformance.getInstance(), 1L);
    }
}
