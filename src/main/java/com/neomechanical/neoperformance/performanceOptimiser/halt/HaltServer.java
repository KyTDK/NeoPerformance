package com.neomechanical.neoperformance.performanceOptimiser.halt;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.performanceOptimiser.utils.Tps;
import com.neomechanical.neoperformance.utils.ActionBar;
import com.neomechanical.neoperformance.utils.messages.MessageUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.List;

public class HaltServer implements Listener, Tps, PerformanceConfigurationSettings {
    public static final CachedData cachedData = new CachedData();
    private final NeoPerformance plugin = NeoPerformance.getInstance();

    @EventHandler()
    public void onTeleport(PlayerTeleportEvent e) {
        if (isServerHalted(e.getPlayer()) && getHaltData().getHaltTeleportation()) {
            e.setCancelled(true);
            if (!cachedData.cachedTeleport.containsKey(e.getPlayer())) {
                cachedData.cachedTeleport.put(e.getPlayer(), e.getTo());
            }
            new ActionBar().SendComponentToPlayer(e.getPlayer(), plugin.getLanguageManager().getString("halted.actionBarTeleportMessage", null));
        }
    }

    @EventHandler()
    public void onMove(PlayerMoveEvent e) {
        if (isServerHalted(e.getPlayer())) {
            Location goTo = e.getTo();
            if (goTo == null) {
                return;
            }
            if (!canMove(e.getFrom().distance(goTo))) {
                e.setCancelled(true);
                new ActionBar().SendComponentToPlayer(e.getPlayer(), plugin.getLanguageManager().getString("halted.actionBarMessage", null));
            }
        }
    }

    @EventHandler()
    public void onExplosion(EntityExplodeEvent e) {
        if (isServerHalted(null) && getHaltData().getHaltExplosions()) {
            List<Entity> list = e.getEntity().getNearbyEntities(10, 10, 10);
            //remove all entities that explode
            e.setCancelled(true);
            for (Entity entity : list) {
                if (entity.getType() == e.getEntity().getType()) {
                    entity.remove();
                }
            }
        }
    }

    //Halt all redstone activity
    @EventHandler()
    public void onRedstone(BlockRedstoneEvent e) {
        if (!cachedData.cachedRedstoneActivity.isEmpty() && !isServerHalted(null)) {
            //e.setNewCurrent(e.getOldCurrent());
            return;
        }
        if (isServerHalted(null) && getHaltData().getHaltRedstone()) {
            Block block = e.getBlock();
            int newCurrent = e.getNewCurrent();
            cachedData.cachedRedstoneActivity.put(block.getState(), newCurrent);
            //Set to 0 to prevent broken circuits
            e.setNewCurrent(0);
        }
    }

    @EventHandler()
    public void onPistonExtend(BlockPistonExtendEvent e) {
        if (isServerHalted(null) && getHaltData().getHaltRedstone()) {
            e.setCancelled(true);
        }
    }
    //event listener for when redstone signal dies out

    @EventHandler()
    public void onPistonRetract(BlockPistonRetractEvent e) {
        if (isServerHalted(null) && getHaltData().getHaltRedstone()) {
            e.setCancelled(true);
        }
    }
    //End of Halt all redstone activity

    @EventHandler()
    public void onChunkLoad(ChunkLoadEvent e) {
        if (isServerHalted(null) && getHaltData().getHaltChunkLoading()) {
            if (e.getChunk().isLoaded()) {
                e.getChunk().unload();
            }
        }
    }

    @EventHandler()
    public void onMobSpawn(EntitySpawnEvent e) {
        if (isServerHalted(null) && getHaltData().getHaltMobSpawning()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onItemMove(InventoryMoveItemEvent e) {
        if (isServerHalted(null) && getHaltData().getHaltInventoryMovement()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onServerCommand(ServerCommandEvent e) {
        if (isServerHalted(null) && getHaltData().getHaltCommandBlock()) {
            if (e.getSender().getName().equals("CONSOLE")) {
                return;
            }
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onItemDrop(PlayerDropItemEvent e) {
        if (isServerHalted(e.getPlayer()) && (getHaltData().getHaltMobSpawning() || getHaltData().getHaltItemDrops())) {
            e.setCancelled(true);
            MessageUtil.sendMM(e.getPlayer(), plugin.getLanguageManager().getString("halted.onItemDrop", null));
            new ActionBar().SendComponentToPlayer(e.getPlayer(), plugin.getLanguageManager().getString("halted.actionBarMessage", null));
        }
    }

    @EventHandler()
    public void onBlockBreak(BlockBreakEvent e) {
        if (isServerHalted(e.getPlayer()) && (getHaltData().getHaltMobSpawning() || getHaltData().getHaltBlockBreaking())) {
            e.setCancelled(true);
            MessageUtil.sendMM(e.getPlayer(), plugin.getLanguageManager().getString("halted.onBlockBreak", null));
            new ActionBar().SendComponentToPlayer(e.getPlayer(), plugin.getLanguageManager().getString("halted.actionBarMessage", null));
        }
    }

    @EventHandler()
    public void onProjectile(ProjectileHitEvent e) {
        if (isServerHalted(null) && getHaltData().getHaltProjectiles()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onProjectile(ProjectileLaunchEvent e) {
        if (isServerHalted(null) && getHaltData().getHaltProjectiles()) {
            e.setCancelled(true);
            if (e.getEntity().getShooter() instanceof Creature) {
                List<Entity> entities = e.getEntity().getNearbyEntities(10, 10, 10);
                entities.removeIf(entity -> entity.getType() != e.getEntity().getType());
                if (entities.size() >= 10) {
                    for (Entity entity : entities) {
                        entity.remove();
                    }
                }
            }
        }
    }

    @EventHandler()
    public void onEntityBread(EntityBreedEvent e) {
        if (isServerHalted(null) && getHaltData().getHaltEntityBreeding()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onEntityInteract(EntityInteractEvent e) {
        if (isServerHalted(null) && getHaltData().getHaltEntityInteractions()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onEntityTarget(EntityTargetEvent e) {
        if (isServerHalted(null) && getHaltData().getHaltEntityTargeting()) {
            e.setCancelled(true);
            List<Entity> entities = e.getEntity().getNearbyEntities(10, 10, 10);
            if (entities.size() >= 10) {
                e.getEntity().remove();
            }
        }
    }

    @EventHandler()
    public void onVehicleCollision(VehicleEntityCollisionEvent e) {
        if (isServerHalted(null) && getHaltData().getHaltVehicleCollisions()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void blockPhysics(BlockPhysicsEvent e) {
        if (isServerHalted(null) && getHaltData().getHaltBlockPhysics()) {
            e.setCancelled(true);
        }
    }
    @EventHandler()
    public void onPlayerJoin(PlayerLoginEvent e) {
        if (isServerHalted(null) && !getHaltData().getAllowJoinWhileHalted()) {
            if (e.getPlayer().hasPermission("neoperformance.bypass")) {
                return;
            }
            //stop player from joining because lag might be due to too many players
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, plugin.getLanguageManager().getString("halted.onJoin", null));
        }
    }
}