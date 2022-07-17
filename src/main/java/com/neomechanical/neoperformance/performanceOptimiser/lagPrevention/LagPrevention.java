package com.neomechanical.neoperformance.performanceOptimiser.lagPrevention;

import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.utils.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;

import java.util.List;

public class LagPrevention implements Listener, PerformanceConfigurationSettings {

    @EventHandler()
    public void onExplosion(EntityExplodeEvent e) {
        //canExplode handles removing entities from the list so that the explosion doesn't happen and lag is prevented
        if (!canExplode(e)) {
            e.setCancelled(true);
        }
    }

    //Unnecessary amount of mobs in area will be capped.
    @EventHandler()
    public void onMobSpawn(EntitySpawnEvent e) {
        if (!canMobSpawn(e)) {
            //Making sure it's not an NPC ensures that nothing is broken by the lag prevention
            if (NPC.isNpc(e.getEntity())) {
                return;
            }
            e.setCancelled(true);
        }
    }

    //Prevents minecart powered lagging machines, apart mobCapRadius
    @EventHandler()
    public void onVehicleCollision(VehicleEntityCollisionEvent e) {
        List<Entity> list = e.getVehicle().getNearbyEntities(getTweakData().getMobCapRadius(), 2, getTweakData().getMobCapRadius());
        list.removeIf(entity -> entity.getType() != e.getVehicle().getType());
        if (list.size() >= 20) {
            e.getVehicle().remove();
        }
    }
}