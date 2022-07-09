package com.neomechanical.neoperformance.performanceOptimiser.config;

import com.neomechanical.neoperformance.performanceOptimiser.config.utils.ConfigUtil;
import com.neomechanical.neoperformance.performanceOptimiser.managers.TweakData;
import com.neomechanical.neoperformance.utils.Logger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;

public class performanceTweaksConfiguration {
    FileConfiguration config = PerformanceConfigCore.config;

    public TweakData loadTweakSettings() {
        new PerformanceConfigCore().createConfig();
        //Performance Tweak Settings
        HashMap<String, Boolean> booleans = new HashMap<>();
        HashMap<String, String> strings = new HashMap<>();
        HashMap<String, String[]> stringsArrays = new HashMap<>();
        HashMap<String, Integer> nums = new HashMap<>();
        //Halt settings
        for (ConfigurationSection configSection : ConfigUtil.getConfigurationSections(config)) {
            for (String configurationSection : configSection.getKeys(false)) {
                Object value = configSection.get(configurationSection);
                if (value instanceof Boolean) {
                    booleans.put(configurationSection, (Boolean) value);
                } else if (value instanceof String) {
                    strings.put(configurationSection, (String) value);
                } else if (value instanceof List) {
                    stringsArrays.put(configurationSection, config.getStringList(configurationSection).toArray(new String[0]));
                } else if (value instanceof Number) {
                    nums.put(configurationSection, (Integer) value);
                } else {
                    if (value == null) {
                        Logger.warn("Null value for " + configurationSection);
                    } else if (value.getClass() == null) {
                        Logger.warn("Null class for " + configurationSection);
                    } else {
                        Logger.warn("Unknown type for " + configurationSection + ": " + value.getClass());
                    }

                }
            }
        }
        return new TweakData(nums.get("tpsHaltAt"), booleans.get("notifyAdmin"), nums.get("mobCap"),
                booleans.get("allowJoinWhileHalted"), nums.get("maxSpeed"), booleans.get("haltTeleportation"), booleans.get("haltExplosions"),
                booleans.get("haltRedstone"), booleans.get("haltChunkLoading"), booleans.get("haltMobSpawning"),
                booleans.get("haltInventoryMovement"), booleans.get("haltCommandBlock"), booleans.get("haltProjectiles"),
                booleans.get("haltEntityBreeding"), booleans.get("haltEntityInteractions"), booleans.get("haltEntityTargeting"),
                booleans.get("haltVehicleCollisions"), booleans.get("haltBlockPhysics"), booleans.get("use_mail_server"),
                strings.get("mail_server_host"), nums.get("mail_server_port"), strings.get("mail_server_username"),
                strings.get("mail_server_password"), stringsArrays.get("recipients"));
    }
}
