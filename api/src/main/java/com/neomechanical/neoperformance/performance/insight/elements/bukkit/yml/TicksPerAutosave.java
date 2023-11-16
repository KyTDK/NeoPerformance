package com.neomechanical.neoperformance.performance.insight.elements.bukkit.yml;

import com.neomechanical.neoperformance.performance.insight.InsightElement;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class TicksPerAutosave extends InsightElement {

    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        File file = new File("bukkit.yml");
        if (!file.exists()) {
            return false;
        }
        return YamlConfiguration.loadConfiguration(file).getDouble("spawn-limits.ticks-per.autosave") > 6000;
    }

    @Override
    public String recommendedValue() {
        return "6000";
    }

    @Override
    public String currentValue() {
        return YamlConfiguration.loadConfiguration(new File("bukkit.yml")).getString("spawn-limits.ticks-per.autosave");
    }

    @Override
    public void fix() {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new File("bukkit.yml"));
        yamlConfiguration.set("spawn-limits.ticks-per.autosave", 6000);
        try {
            yamlConfiguration.save(new File("bukkit.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
