package com.neomechanical.neoperformance;

import com.neomechanical.neoperformance.commands.RegisterCommands;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigCore;
import com.neomechanical.neoperformance.performanceOptimiser.managers.TweakDataManager;
import com.neomechanical.neoperformance.performanceOptimiser.registerOptimiserEvents;
import com.neomechanical.neoperformance.utils.Logger;
import com.neomechanical.neoperformance.utils.updates.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class NeoPerformance extends JavaPlugin {
    private static NeoPerformance instance;
    private static TweakDataManager tweakDataManager;

    public static NeoPerformance getInstance() {
        return instance;
    }

    public static TweakDataManager getTweakDataManager() {
        return tweakDataManager;
    }
    public static void reloadTweakDataManager() {
        PerformanceConfigCore.reloadConfig();
        tweakDataManager = new TweakDataManager();
    }
    private void setInstance(NeoPerformance instance) {
        NeoPerformance.instance = instance;
    }

    @Override
    public void onEnable() {
        ////////////////////////////////////////////////////////////////////////////////////////
        setInstance(this);//This must always be first, as it sets the instance of the plugin//
        ////////////////////////////////////////////////////////////////////////////////////////
        //Metrics
        int pluginId = 15711;
        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(this, pluginId);
        //Config updater
        PerformanceConfigCore config = new PerformanceConfigCore();
        config.createConfig();
        //Check for updates
        new UpdateChecker(this, 103183).getVersion(version -> {
            if (!this.getDescription().getVersion().equals(version)) {
                Logger.info("There is a new update available. Download it at: https://www.spigotmc.org/resources/neoperformance-an-essential-for-any-server.103183/");
            }
        });
        // Plugin startup logic
        tweakDataManager = new TweakDataManager();
        Logger.info("NeoPerformance is enabled and using bStats!");
        registerOptimiserEvents.register(this);
        //Commands
        RegisterCommands.register(this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
