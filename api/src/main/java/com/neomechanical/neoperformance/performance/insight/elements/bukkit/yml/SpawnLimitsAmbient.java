package com.neomechanical.neoperformance.performance.insight.elements.bukkit.yml;

import com.neomechanical.neoperformance.performance.insight.elements.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.YAMLOthers;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class SpawnLimitsAmbient extends InsightElement<Integer> {
    private final YAMLOthers yamlOthers = new YAMLOthers("bukkit.yml");

    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        if (!yamlOthers.configExists()) {
            return false;
        }
        return yamlOthers.getConfig().getDouble("spawn-limits.ambient") > recommendedValue;
    }

    @Override
    public void setDefaultValue() {
        recommendedValue = 4;
    }

    @Override
    public Integer currentValue() {
        return yamlOthers.getConfig().getInt("spawn-limits.ambient");
    }

    @Override
    protected void fixInternally(Player player) {
        YamlConfiguration yamlConfiguration = yamlOthers.getConfig();
        yamlConfiguration.set("spawn-limits.ambient", recommendedValue);
        try {
            yamlConfiguration.save(new File("bukkit.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
