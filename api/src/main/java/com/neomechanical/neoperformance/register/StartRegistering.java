package com.neomechanical.neoperformance.register;

import com.neomechanical.neoutils.NeoUtils;
import com.neomechanical.neoutils.languages.LanguageManager;
import com.neomechanical.neoutils.server.ServerUtils;
import com.neomechanical.neoutils.version.VersionMatcher;
import com.neomechanical.neoutils.version.VersionWrapper;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.moderation.ChatModerationListener;
import com.neomechanical.neoperformance.performance.halt.HaltServer;
import com.neomechanical.neoperformance.performance.lagPrevention.LagPrevention;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.performance.modules.insight.InsightPlaceholders;
import com.neomechanical.neoperformance.performance.modules.smartNotifier.LagChecker;
import com.neomechanical.neoperformance.performance.modules.smartReport.SmartReportPlaceholders;
import com.neomechanical.neoperformance.performance.modules.smartReport.utils.Grading;
import com.neomechanical.neoperformance.performance.performanceHeartBeat.HeartBeat;
import com.neomechanical.neoperformance.performance.utils.TpsUtils;
import com.neomechanical.neoperformance.utils.Logger;
import com.neomechanical.neoperformance.utils.updates.UpdateChecker;
import com.neomechanical.neoperformance.version.halt.HaltWrapperNONLEGACY;
import com.neomechanical.neoperformance.version.halt.IHaltWrapper;
import com.neomechanical.neoperformance.version.heartbeat.IHeartBeat;
import com.neomechanical.neoperformance.version.restore.HeartBeatWrapperNONLEGACY;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

import static org.bukkit.Bukkit.getServer;

public class StartRegistering {
    private final NeoPerformance plugin;
    private final DataManager dataManager;

    public StartRegistering(NeoPerformance plugin) {
        this.plugin = plugin;
        this.dataManager = plugin.getDataManager();
    }

    public void registerLanguageManager() {
        LanguageManager languageManager = new LanguageManager(plugin)
                .setLanguageCode(() -> dataManager.visual().getLanguage())
                .setLanguageFile("de-DE.yml", "en-US.yml", "es-ES.yml", "tr-TR.yml", "fr-FR.yml", "ru-RU.yml", "zh-CN.yml", "pt-BR.yml", "pt-PT.yml")
                .addInternalPlaceholder("%SERVERGRADE%", (Player player) -> Grading.getFancyGrade(Grading.getServerGrade(plugin, dataManager)))
                .addInternalPlaceholder("%TPS%", (Player player) -> TpsUtils.getFancyTps(plugin))
                .addInternalPlaceholder("%TPSHALT%", (Player player) -> TpsUtils.getFancyHaltTps(plugin))
                .addInternalPlaceholder("%SERVERHALTED%", (Player player) -> TpsUtils.fancyIsServerHalted(TpsUtils.getTPS(plugin), plugin))
                .addInternalPlaceholder("%PLAYERCOUNT%", (Player player) -> String.valueOf(Bukkit.getOnlinePlayers().size()))
                .addInternalPlaceholder("%PLAYER%", (Player player) -> player == null ? "None" : player.getName())
                .addInternalPlaceholder("%UPDATESTATUS%", (Player player) -> TpsUtils.getFancyUpdateStatus());
        //Add modules report placeholders
        new SmartReportPlaceholders(languageManager, dataManager).addPlaceholders(plugin);
        //Add insights placeholders
        new InsightPlaceholders(languageManager).addPlaceholders();
        languageManager.set();
    }

    public void registerOptimizers() {
        //Register ability listeners
        if (ServerUtils.getLifePhase() != ServerUtils.ServerLifePhase.UNKNOWN &&
                ServerUtils.getLifePhase() != ServerUtils.ServerLifePhase.STARTUP) {
            IHeartBeat heartBeatWrapper;
            IHaltWrapper haltWrapper;
            try {
                Map<String, VersionWrapper> mappedVersions = new VersionMatcher(NeoUtils.getNeoUtilities().getManagers().getVersionManager()).matchAll();
                VersionWrapper heartbeatMapped = mappedVersions.get("heartbeat");
                VersionWrapper haltMapped = mappedVersions.get("halt");
                if (!(heartbeatMapped instanceof IHeartBeat)) {
                    Logger.warn("Heartbeat version wrapper missing or invalid; using modern implementation.");
                    heartBeatWrapper = new HeartBeatWrapperNONLEGACY();
                } else {
                    heartBeatWrapper = (IHeartBeat) heartbeatMapped;
                }
                if (!(haltMapped instanceof IHaltWrapper)) {
                    Logger.warn("Halt version wrapper missing or invalid; using modern implementation.");
                    haltWrapper = new HaltWrapperNONLEGACY();
                } else {
                    haltWrapper = (IHaltWrapper) haltMapped;
                }
            } catch (Throwable t) {
                Logger.warn("Could not resolve version-specific wrappers (" + t.getClass().getSimpleName() + ": " + t.getMessage()
                        + "). Using modern implementations so the plugin keeps running on newer servers.");
                heartBeatWrapper = new HeartBeatWrapperNONLEGACY();
                haltWrapper = new HaltWrapperNONLEGACY();
            }

            HeartBeat heartBeat = new HeartBeat(plugin, dataManager, heartBeatWrapper);
            heartBeat.start();
            plugin.setHeartBeat(heartBeat);
            getServer().getPluginManager().registerEvents(new HaltServer(plugin, haltWrapper), plugin);
            getServer().getPluginManager().registerEvents(new LagPrevention(plugin), plugin);
            if (dataManager.chatModeration().isEnabled()) {
                getServer().getPluginManager().registerEvents(new ChatModerationListener(plugin), plugin);
            }
            //Update listener
            new UpdateChecker(plugin, 103183).start();
            if (!(dataManager.lagNotifier().getLagNotifierRunInterval() < 1)) {
                new LagChecker(plugin).start();
            }
            Logger.info("NeoPerformance (By KyTDK) is enabled and using bStats!");
        } else {
            //If the server is still starting, then schedule to retry registering optimizers in 5 seconds
            new BukkitRunnable() {
                @Override
                public void run() {
                    registerOptimizers();
                }
            }.runTaskLater(plugin, 20 * 5);
        }
    }
}
