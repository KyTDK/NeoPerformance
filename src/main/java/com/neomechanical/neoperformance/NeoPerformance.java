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
import com.neomechanical.neoperformance.managers.LanguageManager;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.performanceOptimiser.halt.HaltServer;
import com.neomechanical.neoperformance.performanceOptimiser.lagPrevention.LagPrevention;
import com.neomechanical.neoperformance.performanceOptimiser.managers.DataManager;
import com.neomechanical.neoperformance.performanceOptimiser.performanceHeartBeat.HeartBeat;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.LagChecker;
import com.neomechanical.neoperformance.utils.Logger;
import com.neomechanical.neoperformance.utils.updates.UpdateChecker;
import lombok.NonNull;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.neomechanical.neoperformance.utils.updates.IsUpToDate.isUpToDate;

public final class NeoPerformance extends JavaPlugin implements PerformanceConfigurationSettings {
    private static NeoPerformance instance;
    private static LanguageManager languageManager;
    private static BukkitAudiences adventure;
    private static DataManager dataManager;
    private Metrics metrics;

    public static @NonNull BukkitAudiences adventure() {
        if (adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return adventure;
    }

    public static NeoPerformance getInstance() {
        return instance;
    }

    public static DataManager getDataManager() {
        return dataManager;
    }

    public static void reloadTweakDataManager() {
        dataManager.loadTweakSettings();
        NeoPerformance.getInstance().getLanguageManager().loadLanguageConfig();
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    private void setInstance(NeoPerformance instance) {
        NeoPerformance.instance = instance;
    }

    @Override
    public void onEnable() {
        ////////////////////////////////////////////////////////////////////////////////////////
        setInstance(this);//This must always be first, as it sets the instance of the plugin//
        ////////////////////////////////////////////////////////////////////////////////////////
        //set instances
        // Initialize an audiences instance for the plugin
        dataManager = new DataManager();
        dataManager.loadTweakSettings();
        adventure = BukkitAudiences.create(this);
        languageManager = new LanguageManager(this);
        //Check for updates
        new UpdateChecker(this, 103183).getVersion(version -> {
            if (!isUpToDate(this.getDescription().getVersion(), version)) {
                Logger.info("NeoPerformance v" + version + " is out. Download it at: https://www.spigotmc.org/resources/neoperformance-an-essential-for-any-server.103183/");
            }
        });
        // Plugin startup logic
        setupBStats();
        Logger.info("NeoPerformance (By KyTDK) is enabled and using bStats!");
        new BukkitRunnable() {
            @Override
            public void run() {
                registerOptimizers();
            }
        }.runTask(this);
        //Commands
        RegisterCommands.register(this);
    }

    public void setupBStats() {
        int pluginId = 15711;
        metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new SimplePie("Language", () -> NeoPerformance.getInstance().getLanguageManager().getLanguage()));
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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
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
