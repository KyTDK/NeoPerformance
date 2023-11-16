package com.neomechanical.neoperformance.performance.insight.elements.bukkit.yml;

import com.neomechanical.neoperformance.performance.insight.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.YAMLOthers;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ChunkGCPeriodInTicks extends InsightElement {
    private final YAMLOthers yamlOthers = new YAMLOthers("bukkit.yml");
    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        if (!yamlOthers.configExists()) {
            return false;
        }
        return yamlOthers.getConfig().getDouble("spawn-limits.chunk-gc.period-in-ticks") > 300;
    }

    @Override
    public String recommendedValue() {
        return "300";
    }

    @Override
    public String currentValue() {
        return yamlOthers.getConfig().getString("spawn-limits.chunk-gc.period-in-ticks");
    }

    @Override
    public void fix() {
        YamlConfiguration yamlConfiguration = yamlOthers.getConfig();
        yamlConfiguration.set("spawn-limits.chunk-gc.period-in-ticks", 300);
        try {
            yamlConfiguration.save(new File("bukkit.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
