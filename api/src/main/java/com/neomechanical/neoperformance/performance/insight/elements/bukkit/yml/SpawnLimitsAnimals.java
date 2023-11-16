package com.neomechanical.neoperformance.performance.insight.elements.bukkit.yml;

import com.neomechanical.neoperformance.performance.insight.InsightElement;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SpawnLimitsAnimals extends InsightElement {

    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        File file = new File("bukkit.yml");
        if (!file.exists()) {
            return false;
        }
        return YamlConfiguration.loadConfiguration(file).getDouble("spawn-limits.animals") > 10;
    }

    @Override
    public String recommendedValue() {
        return "10";
    }

    @Override
    public String currentValue() {
        return YamlConfiguration.loadConfiguration(new File("bukkit.yml")).getString("spawn-limits.animals");
    }

    @Override
    public void fix() {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new File("bukkit.yml"));
        yamlConfiguration.set("spawn-limits.animals", 10);
        try {
            yamlConfiguration.save(new File("bukkit.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
