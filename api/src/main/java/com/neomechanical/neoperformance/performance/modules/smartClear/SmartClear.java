package com.neomechanical.neoperformance.performance.modules.smartClear;

import com.neomechanical.neoperformance.NeoPerformance;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.List;

public class SmartClear {
    public static void exterminate(List<Entity> entities) {
        for (Entity e : entities) {
            e.remove();
        }
    }

    public static void scanThenExterminate() {
        World[] worlds = Bukkit.getWorlds().toArray(new World[0]);
        List<List<Entity>> clusters = SmartScanner.scan(-1,
                -1,
                NeoPerformance.getInstance().getDataManager().getPerformanceConfig(),
                worlds);
        //For every cluster remove every entity
        for (List<Entity> cluster : clusters) {
            for (Entity e : cluster) {
                e.remove();
            }
        }
    }
}
