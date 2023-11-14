package com.neomechanical.neoperformance.integrations;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import com.neomechanical.neoperformance.integrations.discorsrv.DiscordSRVHook;
import com.neomechanical.neoperformance.integrations.spark.SparkRetrievers;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

@Getter
public class HookIntegrations {
    private final Map<String, Object> integrations = new HashMap<>();

    public HookIntegrations() {
        addIntegration("DiscordSRV", DiscordSRVHook.class);
        addIntegration("spark", SparkRetrievers.class);
    }

    public void addIntegration(String integrationName, Class<?> integrationClass) {
        if (integrationClass != null && Bukkit.getPluginManager().getPlugin(integrationName) != null) {
            try {
                NeoUtils.getNeoUtilities().getFancyLogger().info(integrationName + " hooked into NeoPerformance");
                integrations.put(integrationName, integrationClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace(); // Handle instantiation exception appropriately
            }
        }
    }

    public Object getIntegration(String integrationName) {
        Object integrationClass = integrations.get(integrationName);

        if (integrationClass != null && Bukkit.getPluginManager().getPlugin(integrationName) != null) {
            return integrationClass;
        }
        return null;
    }

    public boolean isInstalled(String integration) {
        return getIntegration(integration) != null;
    }
}
