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
import com.neomechanical.neoperformance.performanceOptimiser.halt.HaltServer;
import com.neomechanical.neoperformance.performanceOptimiser.lagPrevention.LagPrevention;
import com.neomechanical.neoperformance.performanceOptimiser.managers.DataManager;
import com.neomechanical.neoperformance.performanceOptimiser.performanceHeartBeat.HeartBeat;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.LagChecker;
import com.neomechanical.neoperformance.utils.Logger;
import com.neomechanical.neoperformance.utils.updates.UpdateChecker;
import com.neomechanical.neoperformance.version.restore.HeartBeatWrapper;
import com.neomechanical.neoutils.NeoUtils;
import com.neomechanical.neoutils.languages.LanguageManager;
import com.neomechanical.neoutils.version.VersionMatcher;
import com.neomechanical.neoutils.version.VersionWrapper;
import com.neomechanical.neoutils.version.Versioning;
import com.neomechanical.neoutils.version.items.WrapperLEGACY;
import com.neomechanical.neoutils.version.items.WrapperNONLEGACY;
import com.neomechanical.neoutils.version.versions.Versions;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

import static com.neomechanical.neoutils.updates.IsUpToDate.isUpToDate;

public final class NeoPerformance extends NeoUtils {
    private static NeoPerformance instance;
    private DataManager dataManager;
    private Metrics metrics;
    public static NeoPerformance getInstance() {
        return instance;
    }

    public static LanguageManager getLanguageManager() {
        return NeoUtils.getManagers().getLanguageManager();
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    private void setInstance(NeoPerformance instance) {
        NeoPerformance.instance = instance;
    }

    private HeartBeat heartBeat;

    public HeartBeat getHeartBeat() {
        return heartBeat;
    }

    public void reload() {
        dataManager.loadTweakSettings(this);
        getLanguageManager().loadLanguageConfig();
    }

    @Override
    public void onPluginEnable() {
        ////////////////////////////////////////////////////////////////////////////////////////
        setInstance(this);//This must always be first, as it sets the instance of the plugin//
        ////////////////////////////////////////////////////////////////////////////////////////
        // Start heart beat, can't live without it.
        dataManager = new DataManager();
        dataManager.loadTweakSettings(this);
        //Register versions
        new Versioning.VersioningBuilder("heartbeat")
                .addClass(Versions.vLEGACY.toString(), new WrapperLEGACY())
                .addClass(Versions.vNONLEGACY.toString(), new WrapperNONLEGACY())
                .setLegacyFunction((ver) -> !isUpToDate(ver, "1_12_R1"))
                .build()
                .register();
        Map<String, VersionWrapper> mappedVersions = new VersionMatcher(getManagers().getVersionManager()).matchAll();
        heartBeat = new HeartBeat(this, dataManager, (HeartBeatWrapper) mappedVersions.get("heartbeat"));
        heartBeat.start();
        //Set language manager before majority as they depend on its messages.
        new RegisterLanguageManager(this).register();
        //Check for updates
        new UpdateChecker(this, 103183).getVersion(version -> {
            if (!isUpToDate(this.getDescription().getVersion(), version)) {
                Logger.info("NeoPerformance v" + version + " is out. Download it at: https://www.spigotmc.org/resources/neoperformance.103183/");
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
        new RegisterCommands(this).register();
    }

    public void setupBStats() {
        int pluginId = 15711;
        metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new SimplePie("Language", () -> getLanguageManager().getLanguageCode()));
        metrics.addCustomChart(new SimplePie("halt_at_tps", () -> String.valueOf(getDataManager().getTweakData().getTpsHaltAt())));
    }

    public void registerOptimizers() {
        //Register ability listeners
        getServer().getPluginManager().registerEvents(new HaltServer(this), this);
        getServer().getPluginManager().registerEvents(new LagPrevention(this), this);
        new UpdateChecker(this, 103183).start();
        if (!(dataManager.getLagNotifierData().getRunInterval() < 1)) {
            new LagChecker(this).start();
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