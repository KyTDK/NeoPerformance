package com.neomechanical.neoperformance.performance.insight.elements.bukkit.yml;

import com.neomechanical.neoperformance.performance.insight.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.YAMLOthers;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class TicksPerAnimalSpawns extends InsightElement {
    private final YAMLOthers yamlOthers = new YAMLOthers("bukkit.yml");
    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        if (!yamlOthers.configExists()) {
            return false;
        }
        return yamlOthers.getConfig().getDouble("spawn-limits.ticks-per.animal-spawns") > 400;
    }

    @Override
    public String recommendedValue() {
        return "400";
    }

    @Override
    public String currentValue() {
        return yamlOthers.getConfig().getString("spawn-limits.ticks-per.animal-spawns");
    }

    @Override
    public void fix() {
        YamlConfiguration yamlConfiguration = yamlOthers.getConfig();
        yamlConfiguration.set("spawn-limits.ticks-per.animal-spawns", 400);
        try {
            yamlConfiguration.save(new File("bukkit.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
