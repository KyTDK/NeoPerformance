package com.neomechanical.neoperformance.performanceOptimiser.config;

import com.neomechanical.neoperformance.performanceOptimiser.managers.*;
import com.neomechanical.neoperformance.utils.Logger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;

public class performanceTweaksConfiguration {
    FileConfiguration config = null;
    HashMap<String, Boolean> booleans = new HashMap<>();
    HashMap<String, String> strings = new HashMap<>();
    HashMap<String, String[]> stringsArrays = new HashMap<>();
    HashMap<String, Integer> nums = new HashMap<>();

    public void loadTweakSettings(DataManager dataManager) {
        //Performance Tweak Settings
        if (config == null) {
            PerformanceConfigCore configUnit = new PerformanceConfigCore();
            config = configUnit.createConfig();
        }
        //Config updater
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Set halt data
        getDataSet("halt_settings");
        dataManager.setHaltData(new HaltData(booleans.get("allowJoinWhileHalted"), nums.get("maxSpeed"), booleans.get("haltTeleportation"),
                booleans.get("haltExplosions"), booleans.get("haltRedstone"), booleans.get("haltChunkLoading"), booleans.get("haltMobSpawning"),
                booleans.get("haltInventoryMovement"), booleans.get("haltCommandBlock"), booleans.get("haltItemDrops"), booleans.get("haltBlockBreaking"),
                booleans.get("haltProjectiles"), booleans.get("haltEntityBreeding"), booleans.get("haltEntityInteractions"),
                booleans.get("haltEntityTargeting"), booleans.get("haltVehicleCollisions"), booleans.get("haltBlockPhysics")));
        finishSection();
        //Set tweak data
        getDataSet("performance_tweak_settings");
        dataManager.setTweakData(new TweakData(nums.get("tpsHaltAt"), booleans.get("notifyAdmin"), booleans.get("broadcastHalt"), nums.get("mobCap")));
        finishSection();
        //Set visual data
        getDataSet("visual");
        dataManager.setVisualData(new VisualData(strings.get("language")));
        finishSection();
        //Set mail data
        getDataSet("email_notifications");
        dataManager.setMailData(new MailData(booleans.get("use_mail_server"), strings.get("mail_server_host"), nums.get("mail_server_port"),
                strings.get("mail_server_username"), strings.get("mail_server_password"), stringsArrays.get("recipients")));
        finishSection();
    }

    private void getDataSet(String section) {
        //Halt settings
        ConfigurationSection confSection = config.getConfigurationSection(section);
        if (confSection == null) {
            Logger.warn("Section for " + section + " not found in config file.");
            return;
        }
        for (String key : confSection.getKeys(false)) {
            Object value = confSection.get(key);
            if (value instanceof Boolean) {
                booleans.put(key, (Boolean) value);
            } else if (value instanceof String) {
                strings.put(key, (String) value);
            } else if (value instanceof ArrayList) {
                stringsArrays.put(key, confSection.getStringList(key).toArray(new String[0]));
            } else if (value instanceof Number) {
                nums.put(key, (Integer) value);
            } else {
                if (value == null) {
                    Logger.warn("Null value for " + confSection);
                } else if (value.getClass() == null) {
                    Logger.warn("Null class for " + confSection);
                } else {
                    Logger.warn("Unknown type for " + confSection + ": " + value.getClass());
                }

            }
        }
    }

    private void finishSection() {
        booleans.clear();
        strings.clear();
        stringsArrays.clear();
        nums.clear();
    }
}
