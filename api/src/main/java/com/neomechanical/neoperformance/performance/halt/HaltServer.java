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
    private final DataManager dataManager;
    private final IHaltWrapper iHaltWrapper;

    public HaltServer(NeoPerformance plugin, IHaltWrapper iHaltWrapper) {
        this.plugin = plugin;
        this.dataManager = plugin.getDataManager();
        this.iHaltWrapper = iHaltWrapper;
    }

    @EventHandler()
    public void onTeleport(PlayerTeleportEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), e.getPlayer(), plugin) && dataManager.getHaltData().getHaltTeleportation()) {
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
            if (!PerformanceConfigurationSettingsUtils.canMove(dataManager.getHaltData(), e.getFrom().distance(goTo))) {
                e.setCancelled(true);
                new ActionBar().SendComponentToPlayer(e.getPlayer(), getLanguageManager().getString("halted.actionBarMessage", null));
            }
        }
    }

    @EventHandler()
    public void onExplosion(EntityExplodeEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getHaltData().getHaltExplosions()) {
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
        if (!dataManager.getHaltData().getHaltRedstone()) {
            return;
        }
        if (plugin.getDataManager().isRestoringRedstone()) {
            return;
        }
        //Track all redstone activity
        cachedData.cachedRedstoneActivity.add(e.getBlock().getLocation());
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin)) {
            e.setNewCurrent(e.getOldCurrent());
        }
    }

    @EventHandler()
    public void onPistonExtend(BlockPistonExtendEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getHaltData().getHaltRedstone()) {
            e.setCancelled(true);
        }
    }
    //event listener for when redstone signal dies out

    @EventHandler()
    public void onPistonRetract(BlockPistonRetractEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getHaltData().getHaltRedstone()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onMobSpawn(EntitySpawnEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getHaltData().getHaltMobSpawning()) {
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
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getHaltData().getHaltInventoryMovement()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onServerCommand(ServerCommandEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getHaltData().getHaltCommandBlock()) {
            iHaltWrapper.onServerCommand(e);
        }
    }

    @EventHandler()
    public void onItemDrop(PlayerDropItemEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), e.getPlayer(), plugin) && dataManager.getHaltData().getHaltItemDrops()) {
            e.setCancelled(true);
            MessageUtil.sendMM(e.getPlayer(), getLanguageManager().getString("halted.onItemDrop", null));
            new ActionBar().SendComponentToPlayer(e.getPlayer(), getLanguageManager().getString("halted.actionBarMessage", null));
        }
    }

    @EventHandler()
    public void onBlockBreak(BlockBreakEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), e.getPlayer(), plugin) && dataManager.getHaltData().getHaltBlockBreaking()) {
            e.setCancelled(true);
            MessageUtil.sendMM(e.getPlayer(), getLanguageManager().getString("halted.onBlockBreak", null));
            new ActionBar().SendComponentToPlayer(e.getPlayer(), getLanguageManager().getString("halted.actionBarMessage", null));
        }
    }

    @EventHandler()
    public void onPlayerInteraction(PlayerInteractEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), e.getPlayer(), plugin) && dataManager.getHaltData().getHaltPlayerInteractions()) {
            iHaltWrapper.onPlayerInteraction(e);
            MessageUtil.sendMM(e.getPlayer(), getLanguageManager().getString("halted.onPlayerInteract", null));
            new ActionBar().SendComponentToPlayer(e.getPlayer(), getLanguageManager().getString("halted.actionBarMessage", null));
        }
    }

    @EventHandler()
    public void onProjectile(ProjectileHitEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getHaltData().getHaltProjectiles()) {
            iHaltWrapper.onProjectile(e);
        }
    }

    @EventHandler()
    public void onProjectile(ProjectileLaunchEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getHaltData().getHaltProjectiles()) {
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
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getHaltData().getHaltEntityInteractions()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onEntityTarget(EntityTargetEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getHaltData().getHaltEntityTargeting()) {
            e.setCancelled(true);
            List<Entity> entities = e.getEntity().getNearbyEntities(10, 10, 10);
            if (entities.size() >= 10) {
                e.getEntity().remove();
            }
        }
    }

    @EventHandler()
    public void onVehicleCollision(VehicleEntityCollisionEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getHaltData().getHaltVehicleCollisions()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void blockPhysics(BlockPhysicsEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && dataManager.getHaltData().getHaltBlockPhysics()) {
            iHaltWrapper.blockPhysics(e);
        }
    }
    @EventHandler()
    public void onPlayerJoin(PlayerLoginEvent e) {
        if (TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), null, plugin) && !dataManager.getHaltData().getAllowJoinWhileHalted()) {
            if (e.getPlayer().hasPermission("neoperformance.bypass")) {
                return;
            }
            //stop player from joining because lag might be due to too many players
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, getLanguageManager().getString("halted.onPlayerInteract", null));
        }
    }
}