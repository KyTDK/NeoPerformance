package com.neomechanical.neoperformance.performance.insight.elements.spigot.yml;

import com.neomechanical.neoperformance.performance.insight.elements.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.YAMLOthers;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ItemDespawnRate extends InsightElement<Integer> {
    private final YAMLOthers yamlOthers = new YAMLOthers("spigot.yml");

    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        if (!yamlOthers.configExists()) {
            return false;
        }
        return yamlOthers.getConfig().getDouble("world-settings.default.item-despawn-rate") > recommendedValue;
    }

    @Override
    public void setDefaultValue() {
        recommendedValue = 1200;
    }

    @Override
    public Integer currentValue() {
        return yamlOthers.getConfig().getInt("world-settings.default.item-despawn-rate");
    }

    @Override
    protected void fixInternally() {
        YamlConfiguration yamlConfiguration = yamlOthers.getConfig();
        yamlConfiguration.set("world-settings.default.item-despawn-rate", recommendedValue);
        try {
            yamlConfiguration.save(new File("spigot.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
