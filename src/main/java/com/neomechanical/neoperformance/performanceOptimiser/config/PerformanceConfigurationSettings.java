package com.neomechanical.neoperformance.performanceOptimiser.config;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.managers.TweakData;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;

public interface PerformanceConfigurationSettings {
    default TweakData getTweakData() {
        return NeoPerformance.getTweakDataManager().getTweakData();
    }

    default boolean canMobSpawn(EntitySpawnEvent e) {
        List<Entity> list = e.getEntity().getNearbyEntities(10, 10, 10);
        int mobCap = getTweakData().getMobCap();
        if (mobCap == -1) {
            return true;
        }
        return list.size() <= getTweakData().getMobCap();
    }

    default boolean canMove(double instantaneousSpeed) {
        int maxSpeed = getTweakData().getMaxSpeed();
        if (maxSpeed == -1) {
            return true;
        }
        return instantaneousSpeed < maxSpeed;
    }
}
