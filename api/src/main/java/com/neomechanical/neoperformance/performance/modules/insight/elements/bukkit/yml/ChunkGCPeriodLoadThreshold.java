package com.neomechanical.neoperformance.performance.modules.insight.elements.bukkit.yml;

import com.neomechanical.neoperformance.performance.modules.insight.elements.InsightElement;
import com.neomechanical.neoperformance.performance.modules.insight.utils.YAMLOthers;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class ChunkGCPeriodLoadThreshold extends InsightElement<Integer> {
    private final YAMLOthers yamlOthers = new YAMLOthers("bukkit.yml");
    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        if (!yamlOthers.configExists()) {
            return false;
        }
        return yamlOthers.getConfig().getDouble("spawn-limits.chunk-gc.load-threshold") > recommendedValue;
    }

    @Override
    public void setDefaultValue() {
        recommendedValue = 300;
    }

    @Override
    public Integer currentValue() {
        return yamlOthers.getConfig().getInt("spawn-limits.chunk-gc.load-threshold");
    }

    @Override
    protected void fixInternally(Player player) {
        YamlConfiguration yamlConfiguration = yamlOthers.getConfig();
        yamlConfiguration.set("spawn-limits.chunk-gc.load-threshold", recommendedValue);
        try {
            yamlConfiguration.save(new File("bukkit.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
