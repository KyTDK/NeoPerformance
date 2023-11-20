package com.neomechanical.neoperformance.performance.modules.insight.elements.spigot.yml;

import com.neomechanical.neoperformance.performance.modules.insight.elements.InsightElement;
import com.neomechanical.neoperformance.performance.modules.insight.utils.YAMLOthers;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class TicksPerHopperTransfer extends InsightElement<Integer> {
    private final YAMLOthers yamlOthers = new YAMLOthers("spigot.yml");

    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        if (!yamlOthers.configExists()) {
            return false;
        }
        return yamlOthers.getConfig().getDouble("world-settings.default.ticks-per.hopper-transfer") > recommendedValue;
    }

    @Override
    public void setDefaultValue() {
        recommendedValue = 24;
    }

    @Override
    public Integer currentValue() {
        return yamlOthers.getConfig().getInt("world-settings.default.ticks-per.hopper-transfer");
    }

    @Override
    protected void fixInternally(Player player) {
        YamlConfiguration yamlConfiguration = yamlOthers.getConfig();
        yamlConfiguration.set("world-settings.default.ticks-per.hopper-transfer", recommendedValue);
        try {
            yamlConfiguration.save(new File("spigot.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
