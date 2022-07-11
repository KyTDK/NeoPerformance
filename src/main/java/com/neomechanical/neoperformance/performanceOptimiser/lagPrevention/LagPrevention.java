package com.neomechanical.neoperformance.performanceOptimiser.lagPrevention;

import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.HashMap;
import java.util.List;

public class LagPrevention implements Listener, PerformanceConfigurationSettings {
    private final HashMap<String, Long> lastTeleport = new HashMap<>();

    @EventHandler()
    public void onExplosion(EntityExplodeEvent e) {
        //canExplode handles removing entities from the list so that the explosion doesn't happen and lag is prevented
        if (!canExplode(e)) {
            e.setCancelled(true);
        }
    }
    public boolean isNpc(Entity b) {
        List<MetadataValue> metaDataValues = b.getMetadata("NPC");
        for (MetadataValue value : metaDataValues) {
            return value.asBoolean();
        }
        return false;
    }

    //Unnecessary amount of mobs in area will be capped.
    @EventHandler()
    public void onMobSpawn(EntitySpawnEvent e) {
        if (!canMobSpawn(e)) {
            //Making sure it's not an NPC ensures that nothing is broken by the lag prevention
            if (isNpc(e.getEntity())) {
                return;
            }
            e.setCancelled(true);
        }
    }

    //Prevents minecart powered lagging machines
    @EventHandler()
    public void onVehicleCollision(VehicleEntityCollisionEvent e) {
        List<Entity> list = e.getVehicle().getNearbyEntities(10, 10, 10);
        list.removeIf(entity -> entity.getType() != e.getVehicle().getType());
        if (list.size() >= 20) {
            e.getVehicle().remove();
        }
    }
}