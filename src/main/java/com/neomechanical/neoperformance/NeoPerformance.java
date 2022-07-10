package com.neomechanical.neoperformance;

import com.neomechanical.neoperformance.commands.RegisterCommands;
import com.neomechanical.neoperformance.managers.LanguageManager;
import com.neomechanical.neoperformance.performanceOptimiser.managers.TweakDataManager;
import com.neomechanical.neoperformance.performanceOptimiser.registerOptimiserEvents;
import com.neomechanical.neoperformance.utils.Logger;
import com.neomechanical.neoperformance.utils.updates.UpdateChecker;
import lombok.NonNull;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;

import static com.neomechanical.neoperformance.utils.updates.IsUpToDate.isUpToDate;

public final class NeoPerformance extends JavaPlugin {
    private static NeoPerformance instance;
    private static LanguageManager languageManager;
    private static BukkitAudiences adventure;
    private static TweakDataManager tweakDataManager;
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

    public static TweakDataManager getTweakDataManager() {
        return tweakDataManager;
    }

    public static void reloadTweakDataManager() {
        tweakDataManager.loadTweakSettings();
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
        tweakDataManager = new TweakDataManager();
        tweakDataManager.loadTweakSettings();
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
        Logger.info("NeoPerformance is enabled and using bStats!");
        registerOptimiserEvents.register(this);
        //Commands
        RegisterCommands.register(this);
    }

    public void setupBStats() {
        int pluginId = 15711;
        metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new SimplePie("Language", () -> NeoPerformance.getInstance().getLanguageManager().getLanguage()));
        metrics.addCustomChart(new SimplePie("Halt at TPS", () -> String.valueOf(getTweakDataManager().getTweakData().getTpsHaltAt())));
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
