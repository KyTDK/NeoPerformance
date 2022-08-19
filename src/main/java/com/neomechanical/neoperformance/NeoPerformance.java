/*
 * NeoPerformance
 * Copyright (C) 2022 KyTDK
 *
 * This program is exclusive software: you can redistribute it as long as you do not modify any of the files or profit from it.
 *
 *
 */

package com.neomechanical.neoperformance;

import com.neomechanical.neoperformance.commands.RegisterCommands;
import com.neomechanical.neoperformance.managers.RegisterLanguageManager;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.performanceOptimiser.halt.HaltServer;
import com.neomechanical.neoperformance.performanceOptimiser.lagPrevention.LagPrevention;
import com.neomechanical.neoperformance.performanceOptimiser.managers.DataManager;
import com.neomechanical.neoperformance.performanceOptimiser.performanceHeartBeat.HeartBeat;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.LagChecker;
import com.neomechanical.neoperformance.utils.Logger;
import com.neomechanical.neoperformance.utils.updates.UpdateChecker;
import com.neomechanical.neoutils.NeoUtils;
import com.neomechanical.neoutils.languages.LanguageManager;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.scheduler.BukkitRunnable;

import static com.neomechanical.neoperformance.utils.updates.IsUpToDate.isUpToDate;

public final class NeoPerformance extends NeoUtils implements PerformanceConfigurationSettings {
    private static NeoPerformance instance;
    private static DataManager dataManager;
    private Metrics metrics;

    public static NeoPerformance getInstance() {
        return instance;
    }

    public static DataManager getDataManager() {
        return dataManager;
    }

    public static LanguageManager getLanguageManager() {
        return NeoUtils.getManagers().getLanguageManager();
    }

    public static void reload() {
        dataManager.loadTweakSettings();
        getLanguageManager().loadLanguageConfig();
    }

    private void setInstance(NeoPerformance instance) {
        NeoPerformance.instance = instance;
    }

    @Override
    public void onPluginEnable() {
        ////////////////////////////////////////////////////////////////////////////////////////
        setInstance(this);//This must always be first, as it sets the instance of the plugin//
        ////////////////////////////////////////////////////////////////////////////////////////
        // Initialize an audiences instance for the plugin
        dataManager = new DataManager();
        dataManager.loadTweakSettings();
        //Set language manager before majority as they depend on its messages.
        new RegisterLanguageManager().register(this);
        //Check for updates
        new UpdateChecker(this, 103183).getVersion(version -> {
            if (!isUpToDate(this.getDescription().getVersion(), version)) {
                Logger.info("NeoPerformance v" + version + " is out. Download it at: https://www.spigotmc.org/resources/neoperformance-an-essential-for-any-server.103183/");
            }
        });
        // Plugin startup logic
        setupBStats();
        new BukkitRunnable() {
            @Override
            public void run() {
                registerOptimizers();
            }
        }.runTask(this);
        //Commands
        RegisterCommands.register();
    }

    public void setupBStats() {
        int pluginId = 15711;
        metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new SimplePie("Language", () -> getLanguageManager().getLanguageCode()));
        metrics.addCustomChart(new SimplePie("halt_at_tps", () -> String.valueOf(getDataManager().getTweakData().getTpsHaltAt())));
    }

    public void registerOptimizers() {
        //Register ability listeners
        getServer().getPluginManager().registerEvents(new HaltServer(), this);
        getServer().getPluginManager().registerEvents(new LagPrevention(), this);
        new HeartBeat().start();
        new UpdateChecker(this, 103183).start();
        if (!(getLagNotifierData().getRunInterval() < 1)) {
            new LagChecker().start();
        }
        Logger.info("NeoPerformance (By KyTDK) is enabled and using bStats!");
    }

    @Override
    public void onPluginDisable() {
        // Plugin shutdown logic
    }

    /**
     * Returns an instance of the bStats Metrics object
     *
     * @return bStats Metrics object
     */
    @SuppressWarnings("unused")
    public Metrics getMetrics() {
        return metrics;
    }
}
