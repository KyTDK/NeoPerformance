package com.neomechanical.neoperformance.performance.insight.elements.spigot.yml;

import com.neomechanical.neoperformance.performance.insight.InsightElement;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MergeRadiusExp extends InsightElement {

    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        File file = new File("spigot.yml");
        if (!file.exists()) {
            return false;
        }
        return YamlConfiguration.loadConfiguration(file).getDouble("world-settings.default.merge-radius.exp") > 6;
    }

    @Override
    public String recommendedValue() {
        return "6";
    }

    @Override
    public String currentValue() {
        return YamlConfiguration.loadConfiguration(new File("spigot.yml")).getString("world-settings.default.merge-radius.exp");
    }

    @Override
    public void fix() {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new File("spigot.yml"));
        yamlConfiguration.set("world-settings.default.merge-radius.exp", 6);
        try {
            yamlConfiguration.save(new File("spigot.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
