package com.neomechanical.neoperformance.performance.utils;

import com.neomechanical.neoperformance.performance.managers.DataManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;

/**
 * Lag / halt policy derived from loaded configuration. All reads go through {@link DataManager}
 * so callers do not thread {@link com.neomechanical.neoperformance.config.PerformanceConfig} around.
 */
public final class PerformanceConfigurationSettingsUtils {
    private PerformanceConfigurationSettingsUtils() {
    }

    public static boolean canMobSpawn(DataManager dataManager, EntitySpawnEvent e) {
        var tweaks = dataManager.tweakSettings();
        List<Entity> list = e.getEntity().getNearbyEntities(tweaks.getMobCapRadius(), 2, tweaks.getMobCapRadius());
        int mobCap = tweaks.getMobCap();
        if (mobCap == -1 || e.getEntity() instanceof Item) {
            return true;
        }
        return list.size() <= mobCap;
    }

    public static boolean canMove(DataManager dataManager, double instantaneousSpeed) {
        double maxSpeed = dataManager.haltSettings().getMaxSpeed();
        if (maxSpeed == -1) {
            return true;
        }
        return instantaneousSpeed < maxSpeed;
    }

    public static boolean canExplode(DataManager dataManager, EntityExplodeEvent entityExplodeEvent) {
        int tntHalt = dataManager.tweakSettings().getExplosionCap();
        if (tntHalt == -1) {
            return true;
        }
        List<Entity> list = entityExplodeEvent.getEntity().getNearbyEntities(10, 10, 10);
        boolean canExplode = list.size() < tntHalt;
        if (!canExplode) {
            list.removeIf(entity -> entity.getType() != entityExplodeEvent.getEntity().getType());
        }
        return canExplode;
    }
}
