package com.neomechanical.neoperformance.performanceOptimiser.halt;

import com.neomechanical.neoperformance.performanceOptimiser.managers.TweakDataManager;
import com.neomechanical.neoperformance.performanceOptimiser.utils.Tps;
import com.neomechanical.neoperformance.utils.ActionBar;
import com.neomechanical.neoperformance.utils.MessageUtil;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.List;

public class HaltServer implements Listener {
    private final int tpsHalt;
    public static final CachedData cachedData = new CachedData();

    public HaltServer(TweakDataManager tweakDataManager) {
        this.tpsHalt = tweakDataManager.getTweakData().getTpsHaltAt();
    }

    @EventHandler()
    public void onTeleport(PlayerTeleportEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            e.setCancelled(true);
            if (!cachedData.cachedTeleport.containsKey(e.getPlayer())) {
                cachedData.cachedTeleport.put(e.getPlayer(), e.getTo());
            }
            new ActionBar().sendToPlayer(e.getPlayer(), MessageUtil.color("&cServer is currently under heavy load. You will be teleported momentarily."));
        }
    }

    @EventHandler()
    public void onMove(PlayerMoveEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            Location goTo = e.getTo();
            if (goTo == null) {
                return;
            }
            if (e.getFrom().distance(e.getTo()) > 1) {
                e.setCancelled(true);
                new ActionBar().sendToPlayer(e.getPlayer(), MessageUtil.color("&cServer is currently under heavy load. Please try again later."));
            }
        }
    }

    @EventHandler()
    public void onExplosion(EntityExplodeEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
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

    @EventHandler()
    public void onRedstone(BlockRedstoneEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            if (!cachedData.cachedRedstoneActivity.containsKey(e.getBlock())) {
                cachedData.cachedRedstoneActivity.put(e.getBlock(), e.getNewCurrent());
            }
            e.setNewCurrent(0);
        }
    }

    @EventHandler()
    public void onChunkLoad(ChunkLoadEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            if (e.getChunk().isLoaded()) {
                e.getChunk().unload();
            }
        }
    }

    @EventHandler()
    public void onMobSpawn(EntitySpawnEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onItemMove(InventoryMoveItemEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onServerCommand(ServerCommandEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            if (e.getSender().getName().equals("CONSOLE")) {
                return;
            }
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onItemDrop(PlayerDropItemEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(MessageUtil.color("&aThis action has been cancelled to prevent data lass. The server is currently under heavy load, consequently, all items that are dropped will be destroyed."));
            new ActionBar().sendToPlayer(e.getPlayer(), MessageUtil.color("&cServer is currently under heavy load. Please try again later."));
        }
    }

    @EventHandler()
    public void onBlockBreak(BlockBreakEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(MessageUtil.color("&aThis action has been cancelled to prevent data lass. The server is currently under heavy load, consequently, all items that are dropped will be destroyed."));
            new ActionBar().sendToPlayer(e.getPlayer(), MessageUtil.color("&cServer is currently under heavy load. Please try again later."));
        }
    }

    @EventHandler()
    public void onProjectile(ProjectileHitEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onProjectile(ProjectileLaunchEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
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
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onEntityInteract(EntityInteractEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onEntityTarget(EntityTargetEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            e.setCancelled(true);
            List<Entity> entities = e.getEntity().getNearbyEntities(10, 10, 10);
            if (entities.size() >= 10) {
                e.getEntity().remove();
            }
        }
    }

    @EventHandler()
    public void onVehicleCollision(VehicleMoveEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            Vehicle vehicle = e.getVehicle();
            vehicle.teleport(e.getFrom());
        }
    }
    @EventHandler()
    public void blockPhysics(BlockPhysicsEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            e.setCancelled(true);
        }
    }
    @EventHandler()
    public void onPlayerJoin(PlayerLoginEvent e) {
        double tps = new Tps().getTPS();
        if (tps <= tpsHalt) {
            //stop player from joining because lag might be due to too many players
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "This server is currently under heavy load. Please try again later.");
        }
    }
}