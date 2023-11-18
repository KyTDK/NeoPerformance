package com.neomechanical.neoperformance.performance.insight.elements.bukkit.yml;

import com.neomechanical.neoperformance.performance.insight.elements.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.YAMLOthers;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class TicksPerAutosave extends InsightElement<Integer> {
    private final YAMLOthers yamlOthers = new YAMLOthers("bukkit.yml");
    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        if (!yamlOthers.configExists()) {
            return false;
        }
        return yamlOthers.getConfig().getDouble("spawn-limits.ticks-per.autosave") > recommendedValue;
    }

    @Override
    public void setDefaultValue() {
        recommendedValue = 6000;
    }

    @Override
    public Integer currentValue() {
        return yamlOthers.getConfig().getInt("spawn-limits.ticks-per.autosave");
    }

    @Override
    protected void fixInternally(Player player) {
        YamlConfiguration yamlConfiguration = yamlOthers.getConfig();
        yamlConfiguration.set("spawn-limits.ticks-per.autosave", recommendedValue);
        try {
            yamlConfiguration.save(new File("bukkit.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
