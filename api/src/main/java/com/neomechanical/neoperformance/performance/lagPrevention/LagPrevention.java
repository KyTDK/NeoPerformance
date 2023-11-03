package com.neomechanical.neoperformance.performance.lagPrevention;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.utils.PerformanceConfigurationSettingsUtils;
import com.neomechanical.neoperformance.utils.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;

import java.util.List;

public class LagPrevention implements Listener {
    private final NeoPerformance plugin;

    public LagPrevention(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onExplosion(EntityExplodeEvent e) {
        //canExplode handles removing entities from the list so that the explosion doesn't happen and lag is prevented
        if (!PerformanceConfigurationSettingsUtils.canExplode(plugin.getDataManager().getTweakData(), e)) {
            e.setCancelled(true);
        }
    }

    //Unnecessary amount of mobs in area will be capped.
    @EventHandler()
    public void onMobSpawn(EntitySpawnEvent e) {
        if (!PerformanceConfigurationSettingsUtils.canMobSpawn(plugin.getDataManager().getTweakData(), e)) {
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
        List<Entity> list = e.getVehicle().getNearbyEntities(plugin.getDataManager().getTweakData().getMobCapRadius(), 2, plugin.getDataManager().getTweakData().getMobCapRadius());
        list.removeIf(entity -> entity.getType() != e.getVehicle().getType());
        if (list.size() >= 20) {
            e.getVehicle().remove();
        }
    }
}