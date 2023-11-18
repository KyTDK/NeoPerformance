package com.neomechanical.neoperformance.performance.insight.elements.spigot.yml;

import com.neomechanical.neoperformance.performance.insight.elements.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.YAMLOthers;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class EntityActivationRangeMonsters extends InsightElement<Integer> {
    private final YAMLOthers yamlOthers = new YAMLOthers("spigot.yml");

    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        if (!yamlOthers.configExists()) {
            return false;
        }
        return yamlOthers.getConfig().getDouble("world-settings.default.entity-activation-range.monsters") > recommendedValue;
    }

    @Override
    public void setDefaultValue() {
        recommendedValue = 10;
    }

    @Override
    public Integer currentValue() {
        return yamlOthers.getConfig().getInt("world-settings.default.entity-activation-range.monsters");
    }

    @Override
    protected void fixInternally(Player player) {
        YamlConfiguration yamlConfiguration = yamlOthers.getConfig();
        yamlConfiguration.set("world-settings.default.entity-activation-range.monsters", recommendedValue);
        try {
            yamlConfiguration.save(new File("spigot.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
