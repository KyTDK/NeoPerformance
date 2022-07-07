package com.neomechanical.neoperformance;

import com.neomechanical.neoperformance.performanceOptimiser.registerOptimiserEvents;
import com.neomechanical.neoperformance.utils.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public final class NeoPerformance extends JavaPlugin {
    private static NeoPerformance instance;
    public static NeoPerformance getInstance() {
        return instance;
    }
    private void setInstance(NeoPerformance instance) {
        NeoPerformance.instance = instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);
        Logger.info("NeoPerformance is enabled");
        registerOptimiserEvents.register(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
