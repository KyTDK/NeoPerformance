package com.neomechanical.neoperformance.performance.halt;

import com.neomechanical.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.performance.utils.TpsUtils;
import com.neomechanical.neoperformance.utils.ActionBar;
import com.neomechanical.neoperformance.utils.NPC;
import com.neomechanical.neoperformance.utils.OfflinePermissionUtils;
import com.neomechanical.neoperformance.version.halt.IHaltWrapper;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
import java.util.UUID;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;

public class HaltServer implements Listener {
    private static final BlockFace[] DIRECT_NEIGHBORS = new BlockFace[]{
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.SOUTH,
            BlockFace.EAST,
            BlockFace.WEST
    };
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
        if (isHalted(e.getPlayer()) && dataManager.haltSettings().isHaltTeleportation()) {
            if (NPC.isNpc(e.getPlayer())) {
                return;
            }
            e.setCancelled(true);
            cachedData.cacheTeleport(e.getPlayer(), e.getTo());
            new ActionBar().SendComponentToPlayer(e.getPlayer(), getLanguageManager().getString("halted.actionBarTeleportMessage", null));
        }
    }

    @EventHandler()
    public void onMove(PlayerMoveEvent e) {
        if (isHalted(e.getPlayer())) {
            Location goTo = e.getTo();
            if (goTo == null) {
                return;
            }
            if (!dataManager.allowsPlayerMoveSpeed(e.getFrom().distance(goTo))) {
                e.setCancelled(true);
                new ActionBar().SendComponentToPlayer(e.getPlayer(), getLanguageManager().getString("halted.actionBarMessage", null));
            }
        }
    }

    @EventHandler()
    public void onExplosion(EntityExplodeEvent e) {
        if (isHalted(null) && dataManager.haltSettings().isHaltExplosions()) {
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
        if (!dataManager.haltSettings().isHaltRedstone()) {
            return;
        }
        if (!isHalted(null)) {
            return;
        }

        if (e.getOldCurrent() != e.getNewCurrent()) {
            cacheRedstoneImpact(e.getBlock());
        }
        e.setNewCurrent(e.getOldCurrent());
    }

    @EventHandler()
    public void onPistonExtend(BlockPistonExtendEvent e) {
        if (isHalted(null) && dataManager.haltSettings().isHaltRedstone()) {
            e.setCancelled(true);
            cachePistonImpact(e.getBlock(), e.getBlocks(), e.getDirection(), false);
        }
    }
    //event listener for when redstone signal dies out

    @EventHandler()
    public void onPistonRetract(BlockPistonRetractEvent e) {
        if (isHalted(null) && dataManager.haltSettings().isHaltRedstone()) {
            e.setCancelled(true);
            cachePistonImpact(e.getBlock(), e.getBlocks(), e.getDirection(), true);
        }
    }

    @EventHandler()
    public void onMobSpawn(EntitySpawnEvent e) {
        if (isHalted(null) && dataManager.haltSettings().isHaltMobSpawning()) {
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
        if (isHalted(null) && dataManager.haltSettings().isHaltInventoryMovement()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onServerCommand(ServerCommandEvent e) {
        if (isHalted(null) && dataManager.haltSettings().isHaltCommandBlock()) {
            iHaltWrapper.onServerCommand(e);
        }
    }

    @EventHandler()
    public void onItemDrop(PlayerDropItemEvent e) {
        if (isHalted(e.getPlayer()) && dataManager.haltSettings().isHaltItemDrops()) {
            e.setCancelled(true);
            sendHaltFeedback(e.getPlayer(), "halted.onItemDrop");
        }
    }

    @EventHandler()
    public void onBlockBreak(BlockBreakEvent e) {
        if (isHalted(e.getPlayer()) && dataManager.haltSettings().isHaltBlockBreaking()) {
            e.setCancelled(true);
            sendHaltFeedback(e.getPlayer(), "halted.onBlockBreak");
        }
    }

    @EventHandler()
    public void onPlayerInteraction(PlayerInteractEvent e) {
        if (isHalted(e.getPlayer()) && dataManager.haltSettings().isHaltPlayerInteractions()) {
            iHaltWrapper.onPlayerInteraction(e);
            sendHaltFeedback(e.getPlayer(), "halted.onPlayerInteract");
        }
    }

    @EventHandler()
    public void onProjectile(ProjectileHitEvent e) {
        if (isHalted(null) && dataManager.haltSettings().isHaltProjectiles()) {
            iHaltWrapper.onProjectile(e);
        }
    }

    @EventHandler()
    public void onProjectile(ProjectileLaunchEvent e) {
        if (isHalted(null) && dataManager.haltSettings().isHaltProjectiles()) {
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
        if (isHalted(null) && dataManager.haltSettings().isHaltEntityInteractions()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onEntityTarget(EntityTargetEvent e) {
        if (isHalted(null) && dataManager.haltSettings().isHaltEntityTargeting()) {
            e.setCancelled(true);
            List<Entity> entities = e.getEntity().getNearbyEntities(10, 10, 10);
            if (entities.size() >= 10) {
                e.getEntity().remove();
            }
        }
    }

    @EventHandler()
    public void onVehicleCollision(VehicleEntityCollisionEvent e) {
        if (isHalted(null) && dataManager.haltSettings().isHaltVehicleCollisions()) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void blockPhysics(BlockPhysicsEvent e) {
        if (isHalted(null) && dataManager.haltSettings().isHaltBlockPhysics()) {
            iHaltWrapper.blockPhysics(e);
        }
    }
    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        if (!isHalted(null) || dataManager.haltSettings().isAllowJoinWhileHalted()) {
            return;
        }
        if (canJoinWhileHalted(event.getUniqueId())) {
            return;
        }
        event.disallow(
                AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                getLanguageManager().getString("halted.onJoin", null)
        );
    }

    private boolean canJoinWhileHalted(UUID uuid) {
        return OfflinePermissionUtils.hasPermission(uuid, "neoperformance.bypass")
                || OfflinePermissionUtils.hasPermission(uuid, "neoperformance.bypass.auto");
    }

    private boolean isHalted(org.bukkit.entity.Player player) {
        return TpsUtils.isServerHalted(TpsUtils.getTPS(plugin), player, plugin);
    }

    private void sendHaltFeedback(org.bukkit.entity.Player player, String messageKey) {
        MessageUtil.sendMM(player, getLanguageManager().getString(messageKey, null));
        new ActionBar().SendComponentToPlayer(player, getLanguageManager().getString("halted.actionBarMessage", null));
    }

    private void cacheRedstoneImpact(Block block) {
        cacheBlockAndNeighbors(block);
    }

    private void cachePistonImpact(Block pistonBase, List<Block> movedBlocks, BlockFace movementDirection, boolean retracting) {
        cacheBlockAndNeighbors(pistonBase);
        for (Block movedBlock : movedBlocks) {
            cacheBlockAndNeighbors(movedBlock);
            if (retracting) {
                cacheBlockAndNeighbors(movedBlock.getRelative(movementDirection.getOppositeFace()));
            } else {
                cacheBlockAndNeighbors(movedBlock.getRelative(movementDirection));
            }
        }
    }

    private void cacheBlockAndNeighbors(Block block) {
        cachedData.cacheRedstoneLocation(block.getLocation());
        for (BlockFace face : DIRECT_NEIGHBORS) {
            cachedData.cacheRedstoneLocation(block.getRelative(face).getLocation());
        }
    }
}
