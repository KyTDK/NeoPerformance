package com.neomechanical.neoperformance.performanceOptimiser.config;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.managers.data.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;

public interface PerformanceConfigurationSettings {
    default TweakData getTweakData() {
        return NeoPerformance.getDataManager().getTweakData();
    }

    default HaltData getHaltData() {
        return NeoPerformance.getDataManager().getHaltData();
    }

    default MailData getMailData() {
        return NeoPerformance.getDataManager().getMailData();
    }

    default VisualData getVisualData() {
        return NeoPerformance.getDataManager().getVisualData();
    }

    default CommandData getCommandData() {
        return NeoPerformance.getDataManager().getCommandData();
    }

    default LagNotifierData getLagNotifierData() {
        return NeoPerformance.getDataManager().getLagNotifierData();
    }

    default boolean canMobSpawn(EntitySpawnEvent e) {
        List<Entity> list = e.getEntity().getNearbyEntities(getTweakData().getMobCapRadius(), 2, getTweakData().getMobCapRadius());
        int mobCap = getTweakData().getMobCap();
        if (mobCap == -1) {
            return true;
        }
        return list.size() <= getTweakData().getMobCap();
    }

    default boolean canMove(double instantaneousSpeed) {
        int maxSpeed = getHaltData().getMaxSpeed();
        if (maxSpeed == -1) {
            return true;
        }
        return instantaneousSpeed < maxSpeed;
    }

    default boolean canExplode(EntityExplodeEvent entityExplodeEvent) {
        //Get the list of explosives nearby the explosion
        int tntHalt = getTweakData().getExplosionCap();
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
