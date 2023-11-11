package com.neomechanical.neoperformance.performance.utils;

import com.neomechanical.neoperformance.config.PerformanceConfig;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;

public class PerformanceConfigurationSettingsUtils {
    private PerformanceConfigurationSettingsUtils() {
    }

    public static boolean canMobSpawn(PerformanceConfig performanceConfig, EntitySpawnEvent e) {
        List<Entity> list = e.getEntity().getNearbyEntities(performanceConfig.getPerformanceTweakSettings().getMobCapRadius(), 2, performanceConfig.getPerformanceTweakSettings().getMobCapRadius());
        int mobCap = performanceConfig.getPerformanceTweakSettings().getMobCap();
        if (mobCap == -1 || e.getEntity() instanceof Item) {
            return true;
        }
        return list.size() <= performanceConfig.getPerformanceTweakSettings().getMobCap();
    }

    public static boolean canMove(PerformanceConfig performanceConfig, double instantaneousSpeed) {
        double maxSpeed = performanceConfig.getHaltSettings().getMaxSpeed();
        if (maxSpeed == -1) {
            return true;
        }
        return instantaneousSpeed < maxSpeed;
    }

    public static boolean canExplode(PerformanceConfig performanceConfig, EntityExplodeEvent entityExplodeEvent) {
        //Get the list of explosives nearby the explosion
        int tntHalt = performanceConfig.getPerformanceTweakSettings().getExplosionCap();
        if (tntHalt == -1) {
            return true;
        }
        List<Entity> list = entityExplodeEvent.getEntity().getNearbyEntities(10, 10, 10);
        boolean canExplode = list.size() < tntHalt;
        if (!canExplode) {
            //remove all in list so that the explosion doesn't happen and lag is prevented
            list.removeIf(entity -> entity.getType() != entityExplodeEvent.getEntity().getType());
        }
        //If the list is greater than the tntHalt, cancel the explosion
        return canExplode;
    }
}
