package com.neomechanical.neoperformance.performance.insight.elements.bukkit.yml;

import com.neomechanical.neoperformance.performance.insight.elements.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.YAMLOthers;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class TicksPerAnimalSpawns extends InsightElement<Integer> {
    private final YAMLOthers yamlOthers = new YAMLOthers("bukkit.yml");
    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        if (!yamlOthers.configExists()) {
            return false;
        }
        return yamlOthers.getConfig().getDouble("spawn-limits.ticks-per.animal-spawns") > recommendedValue;
    }

    @Override
    public void setDefaultValue() {
        recommendedValue = 400;
    }

    @Override
    public Integer currentValue() {
        return yamlOthers.getConfig().getInt("spawn-limits.ticks-per.animal-spawns");
    }

    @Override
    protected void fixInternally() {
        YamlConfiguration yamlConfiguration = yamlOthers.getConfig();
        yamlConfiguration.set("spawn-limits.ticks-per.animal-spawns", recommendedValue);
        try {
            yamlConfiguration.save(new File("bukkit.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
