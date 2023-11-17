package com.neomechanical.neoperformance.performance.halt;

import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.performance.utils.PerformanceConfigurationSettingsUtils;
import com.neomechanical.neoperformance.performance.utils.TpsUtils;
import com.neomechanical.neoperformance.utils.ActionBar;
import com.neomechanical.neoperformance.utils.NPC;
import com.neomechanical.neoperformance.version.halt.IHaltWrapper;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;

import java.util.List;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;

public class HaltServer implements Listener {
    public static final CachedData cachedData = new CachedData();
    private final NeoPerformance plugin;
    private final IHaltWrapper iHaltWrapper;
    private final DataManager dataManager;

    public HaltServer(NeoPerformance plugin, IHaltWrapper iHaltWrapper) {
        this.plugin = plugin;
        this.dataManager = plugin.getDataManager();
        this.iHaltWrapper = iHaltWrapper;
    }

    @EventHandler()
    public void onTeleport(PlayerTeleportEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), e.getPlayer(), plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltTeleportation()) {
            if (NPC.isNpc(e.getPlayer())) {
                return;
            }
            e.setCancelled(true);
            cachedData.cachedTeleport.putIfAbsent(e.getPlayer(), e.getTo());
            new ActionBar().SendComponentToPlayer(e.getPlayer(), getLanguageManager().getString("halted.actionBarTeleportMessage", null));
        }
    }

    @EventHandler()
    public void onMove(PlayerMoveEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), e.getPlayer(), plugin)) {
            Location goTo = e.getTo();
            if (goTo == null) {
                return;
            }
            if (!PerformanceConfigurationSettingsUtils.canMove(dataManager.getPerformanceConfig(), e.getFrom().distance(goTo))) {
                e.setCancelled(true);
                new ActionBar().SendComponentToPlayer(e.getPlayer(), getLanguageManager().getString("halted.actionBarMessage", null));
            }
        }
    }

    @EventHandler()
    public void onExplosion(EntityExplodeEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltExplosions()) {
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
    @EventHandler
    public void onRedstone(BlockRedstoneEvent e) {
        if (!dataManager.getPerformanceConfig().getHaltSettings().isHaltRedstone()) {
            return;
        }
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin)) {
            e.setNewCurrent(e.getOldCurrent());
        }
        if (e.getNewCurrent() != 0) {
            BlockState originalState = e.getBlock().getState();
            cachedData.cachedRedstoneActivity.put(e.getBlock().getLocation(), originalState);
        } else {
            cachedData.cachedRedstoneActivity.remove(e.getBlock().getLocation());
        }
    }

    @EventHandler()
    public void onPistonExtend(BlockPistonExtendEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltRedstone()) {
            e.setCancelled(true);
        }
    }
    //event listener for when redstone signal dies out

    @EventHandler()
    public void onPistonRetract(BlockPistonRetractEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltRedstone()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onMobSpawn(EntitySpawnEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltMobSpawning()) {
            if (NPC.isNpc(e.getEntity())) {
                return;
            }
            if (e.getEntity() instanceof Item) {
                return;
            }
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onItemMove(InventoryMoveItemEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltInventoryMovement()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onServerCommand(ServerCommandEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltCommandBlock()) {
            iHaltWrapper.onServerCommand(e);
        }
    }

    @EventHandler()
    public void onItemDrop(PlayerDropItemEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), e.getPlayer(), plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltItemDrops()) {
            e.setCancelled(true);
            MessageUtil.sendMM(e.getPlayer(), getLanguageManager().getString("halted.onItemDrop", null));
            new ActionBar().SendComponentToPlayer(e.getPlayer(), getLanguageManager().getString("halted.actionBarMessage", null));
        }
    }

    @EventHandler()
    public void onBlockBreak(BlockBreakEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), e.getPlayer(), plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltBlockBreaking()) {
            e.setCancelled(true);
            MessageUtil.sendMM(e.getPlayer(), getLanguageManager().getString("halted.onBlockBreak", null));
            new ActionBar().SendComponentToPlayer(e.getPlayer(), getLanguageManager().getString("halted.actionBarMessage", null));
        }
    }

    @EventHandler()
    public void onPlayerInteraction(PlayerInteractEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), e.getPlayer(), plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltPlayerInteractions()) {
            iHaltWrapper.onPlayerInteraction(e);
            MessageUtil.sendMM(e.getPlayer(), getLanguageManager().getString("halted.onPlayerInteract", null));
            new ActionBar().SendComponentToPlayer(e.getPlayer(), getLanguageManager().getString("halted.actionBarMessage", null));
        }
    }

    @EventHandler()
    public void onProjectile(ProjectileHitEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltProjectiles()) {
            iHaltWrapper.onProjectile(e);
        }
    }

    @EventHandler()
    public void onProjectile(ProjectileLaunchEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltProjectiles()) {
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
    public void onEntityInteract(EntityInteractEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltEntityInteractions()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onEntityTarget(EntityTargetEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltEntityTargeting()) {
            e.setCancelled(true);
            List<Entity> entities = e.getEntity().getNearbyEntities(10, 10, 10);
            if (entities.size() >= 10) {
                e.getEntity().remove();
            }
        }
    }

    @EventHandler()
    public void onVehicleCollision(VehicleEntityCollisionEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltVehicleCollisions()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void blockPhysics(BlockPhysicsEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getPerformanceConfig().getHaltSettings().isHaltBlockPhysics()) {
            iHaltWrapper.blockPhysics(e);
        }
    }
    @EventHandler()
    public void onPlayerJoin(PlayerLoginEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && !dataManager.getPerformanceConfig().getHaltSettings().isAllowJoinWhileHalted()) {
            if (e.getPlayer().hasPermission("neoperformance.bypass")) {
                return;
            }
            //stop player from joining because lag might be due to too many players
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, getLanguageManager().getString("halted.onPlayerInteract", null));
        }
    }
}