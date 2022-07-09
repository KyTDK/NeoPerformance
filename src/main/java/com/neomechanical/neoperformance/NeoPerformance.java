package com.neomechanical.neoperformance;

import com.neomechanical.neoperformance.commands.CommandManager;
import com.neomechanical.neoperformance.commands.RegisterCommands;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigCore;
import com.neomechanical.neoperformance.performanceOptimiser.managers.TweakDataManager;
import com.neomechanical.neoperformance.performanceOptimiser.registerOptimiserEvents;
import com.neomechanical.neoperformance.utils.Logger;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class NeoPerformance extends JavaPlugin {
    private static NeoPerformance instance;
    private static TweakDataManager tweakDataManager;
    private Metrics metrics;

    public static NeoPerformance getInstance() {
        return instance;
    }

    private static CommandManager commandManager;

    public static CommandManager getCommandManager() {
        return commandManager;
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
        int pluginId = 15711; // <-- Replace with the id of your plugin!
        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(this, pluginId);
        PerformanceConfigCore config = new PerformanceConfigCore();
        config.createConfig();
        // Plugin startup logic
        tweakDataManager = new TweakDataManager();
        Logger.info("NeoPerformance is enabled and using bStats!");
        registerOptimiserEvents.register(this);
        commandManager = RegisterCommands.register(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
