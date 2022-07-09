package com.neomechanical.neoperformance.performanceOptimiser.lagPrevention;

import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.utils.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.HashMap;
import java.util.List;

public class lagPrevention implements Listener, PerformanceConfigurationSettings {
    private final HashMap<String, Long> lastTeleport = new HashMap<>();
    private final HashMap<CommandSender, Long> lastCommand = new HashMap<>();

    @EventHandler()
    public void onExplosion(EntityExplodeEvent e) {
        List<Entity> list = e.getEntity().getNearbyEntities(10, 10, 10);
        int tntHalt = 18;
        list.removeIf(entity -> entity.getType() != e.getEntity().getType());
        if (list.size() > tntHalt) {
            e.setCancelled(true);
            //remove all in list
            for (Entity entity : list) {
                entity.remove();
            }
        }
    }
    public boolean isNpc(Entity b) {
        List<MetadataValue> metaDataValues = b.getMetadata("NPC");
        for (MetadataValue value : metaDataValues) {
            return value.asBoolean();
        }
        return false;
    }
    @EventHandler()
    public void onMobSpawn(EntitySpawnEvent e) {
        if (isNpc(e.getEntity())) {
            return;
        }
        if (!canMobSpawn(e)) {
            e.setCancelled(true);
        }
    }
    @EventHandler()
    public void onTeleport(PlayerTeleportEvent e) {
        if (isNpc(e.getPlayer())) {
            return;
        }
        if (!e.getCause().equals(PlayerTeleportEvent.TeleportCause.COMMAND)) {
            return;
        }
        if (lastTeleport.containsKey(e.getPlayer().getName())) {
            long last = lastTeleport.get(e.getPlayer().getName());
            if (System.currentTimeMillis() - last < 10) {
                e.getPlayer().sendMessage(MessageUtil.color("&cYou can't teleport too fast!"));
                e.setCancelled(true);
            } else {
                lastTeleport.put(e.getPlayer().getName(), System.currentTimeMillis());
            }
            return;
        }
        lastTeleport.put(e.getPlayer().getName(), System.currentTimeMillis());
    }
    @EventHandler()
    public void onServerCommand(ServerCommandEvent e) {
        if (lastCommand.containsKey(e.getSender())) {
            long last = lastCommand.get(e.getSender());
            if (System.currentTimeMillis() - last < 50) {
                e.setCancelled(true);
            } else {
                lastCommand.put(e.getSender(), System.currentTimeMillis());
            }
            return;
        }
        lastCommand.put(e.getSender(), System.currentTimeMillis());
    }

    @EventHandler()
    public void onVehicleCollision(VehicleEntityCollisionEvent e) {
        List<Entity> list = e.getVehicle().getNearbyEntities(10, 10, 10);
        list.removeIf(entity -> entity.getType() != e.getVehicle().getType());
        if (list.size() >= 20) {
            e.getVehicle().remove();
        }
    }
}