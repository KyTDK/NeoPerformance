/*
 * NeoPerformance
 * Copyright (C) 2022 KyTDK
 *
 * This program is exclusive software: you can redistribute it as long as you do not modify any of the files or profit from it.
 *
 *
 */

package com.neomechanical.neoperformance;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import com.neomechanical.neoconfig.neoutils.languages.LanguageManager;
import com.neomechanical.neoconfig.neoutils.version.Versioning;
import com.neomechanical.neoconfig.neoutils.version.versions.Versions;
import com.neomechanical.neoperformance.commands.RegisterCommands;
import com.neomechanical.neoperformance.managers.DataHandler;
import com.neomechanical.neoperformance.performance.haltActions.RegisterHaltActions;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.performance.performanceHeartBeat.HeartBeat;
import com.neomechanical.neoperformance.performance.utils.TpsUtils;
import com.neomechanical.neoperformance.register.StartRegistering;
import com.neomechanical.neoperformance.utils.Logger;
import com.neomechanical.neoperformance.utils.updates.UpdateChecker;
import com.neomechanical.neoperformance.version.halt.HaltWrapperLEGACY;
import com.neomechanical.neoperformance.version.halt.HaltWrapperNONLEGACY;
import com.neomechanical.neoperformance.version.restore.HeartBeatWrapperLEGACY;
import com.neomechanical.neoperformance.version.restore.HeartBeatWrapperNONLEGACY;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.neomechanical.neoconfig.neoutils.updates.IsUpToDate.isUpToDate;

public final class NeoPerformance extends JavaPlugin {
    @Getter
    private static NeoPerformance instance;
    @Getter
    private DataManager dataManager;
    private Metrics metrics;

    public static LanguageManager getLanguageManager() {
        return NeoUtils.getNeoUtilities().getManagers().getLanguageManager();
    }

    private void setInstance(NeoPerformance instance) {
        NeoPerformance.instance = instance;
    }

    @Getter
    private HeartBeat heartBeat;

    public void setHeartBeat(HeartBeat heartBeat) {
        this.heartBeat = heartBeat;
    }

    private static DataHandler dataHandler;

    public DataHandler getPerformanceDataHandler() {
        return dataHandler;
    }

    public void reload() {
        dataManager.loadTweakSettings(this);
        getLanguageManager().loadLanguageConfig();
    }

    @Override
    public void onEnable() {
        ////////////////////////////////////////////////////////////////////////////////////////
        setInstance(this);// This must always be first, as it sets the instance of the plugin //
        ////////////////////////////////////////////////////////////////////////////////////////
        dataManager = new DataManager();
        dataManager.loadTweakSettings(this);
        //Set POJOs
        dataHandler = new DataHandler();
        //Register versions
        new Versioning.VersioningBuilder("heartbeat")
                .addClass(Versions.vLEGACY.toString(), HeartBeatWrapperLEGACY.class)
                .addClass(Versions.vNONLEGACY.toString(), HeartBeatWrapperNONLEGACY.class)
                .setLegacyFunction((ver) -> !isUpToDate(ver, Versions.v1_13_R1.toString()))
                .build()
                .register();
        new Versioning.VersioningBuilder("halt")
                .addClass(Versions.vLEGACY.toString(), HaltWrapperLEGACY.class)
                .addClass(Versions.vNONLEGACY.toString(), HaltWrapperNONLEGACY.class)
                .setLegacyFunction((ver) -> !isUpToDate(ver, Versions.v1_13_R1.toString()))
                .build()
                .register();
        new RegisterHaltActions(this).registerActions();
        StartRegistering startRegistering = new StartRegistering(this);
        new BukkitRunnable() {
            @Override
            public void run() {
                startRegistering.registerOptimizers();
            }
        }.runTask(this);
        //Set language manager before majority as they depend on its messages.
        startRegistering.registerLanguageManager();
        //Check for updates
        new UpdateChecker(this, 103183).getVersion(version -> {
            if (!isUpToDate(this.getDescription().getVersion(), version)) {
                Logger.info("NeoPerformance v" + version + " is out. Download it at: https://www.spigotmc.org/resources/neoperformance.103183/");
            }
        });
        // Plugin startup logic
        setupBStats();
        //Commands
        new RegisterCommands(this).register();
    }

    public void setupBStats() {
        int pluginId = 15711;
        metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new SimplePie("Language", () -> getLanguageManager().getLanguageCode()));
        metrics.addCustomChart(new SimplePie("halt_at_tps", () -> String.valueOf(getDataManager().getPerformanceConfig().getPerformanceTweakSettings().getTpsHaltAt())));
        metrics.addCustomChart(new SimplePie("current_server_tps", () -> String.valueOf((double) Math.round(TpsUtils.getTPS(this) * 100) / 100)));
    }

    @Override
    public void onDisable() {
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