package com.neomechanical.neoperformance.performance.insight.elements.spigot.yml;

import com.neomechanical.neoperformance.performance.insight.InsightElement;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ItemDespawnRate extends InsightElement {

    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        File file = new File("spigot.yml");
        if (!file.exists()) {
            return false;
        }
        return YamlConfiguration.loadConfiguration(file).getDouble("world-settings.default.item-despawn-rate") > 1200;
    }

    @Override
    public String recommendedValue() {
        return "1200";
    }

    @Override
    public String currentValue() {
        return YamlConfiguration.loadConfiguration(new File("spigot.yml")).getString("world-settings.default.item-despawn-rate");
    }

    @Override
    public void fix() {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new File("spigot.yml"));
        yamlConfiguration.set("world-settings.default.item-despawn-rate", 1200);
        try {
            yamlConfiguration.save(new File("spigot.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
