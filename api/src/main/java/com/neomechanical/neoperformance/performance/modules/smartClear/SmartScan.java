package com.neomechanical.neoperformance.performance.modules.smartClear;

import lombok.Data;
import org.bukkit.entity.Entity;

import java.util.List;

@Data
public class SmartScan {
    private final List<List<Entity>> clusters;
    private final int toClear;
}
