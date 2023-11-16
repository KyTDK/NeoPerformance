package com.neomechanical.neoperformance.performance.insight.elements.spigot.yml;

import com.neomechanical.neoperformance.performance.insight.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.YAMLOthers;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class EntityActivationRangeAnimals extends InsightElement {
    private final YAMLOthers yamlOthers = new YAMLOthers("spigot.yml");
    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        if (!yamlOthers.configExists()) {
            return false;
        }
        return yamlOthers.getConfig().getDouble("world-settings.default.entity-activation-range.animals") > 8;
    }

    @Override
    public String recommendedValue() {
        return "8";
    }

    @Override
    public String currentValue() {
        return yamlOthers.getConfig().getString("world-settings.default.entity-activation-range.animals");
    }

    @Override
    public void fix() {
        YamlConfiguration yamlConfiguration = yamlOthers.getConfig();
        yamlConfiguration.set("world-settings.default.entity-activation-range.animals", 8);
        try {
            yamlConfiguration.save(new File("spigot.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
