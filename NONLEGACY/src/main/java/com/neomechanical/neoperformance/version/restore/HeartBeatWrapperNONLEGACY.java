package com.neomechanical.neoperformance.version.restore;

import com.neomechanical.neoutils.NeoUtils;
import com.neomechanical.neoperformance.version.heartbeat.IHeartBeat;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class HeartBeatWrapperNONLEGACY implements IHeartBeat {
    private static final int REDSTONE_RESTORE_BATCH_SIZE = 64;
    private static final int REDSTONE_RESTORE_MAX_PASSES = 40;
    private static final BlockFace[] DIRECT_NEIGHBORS = new BlockFace[]{
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.SOUTH,
            BlockFace.EAST,
            BlockFace.WEST
    };

    @Override
    public void restoreServer(LinkedHashMap<Player, Location> cachedTeleport, LinkedHashSet<Location> cachedRedstoneActivity) {
        restoreTeleports(cachedTeleport);
        restoreRedstone(cachedRedstoneActivity);
    }

    private void restoreTeleports(LinkedHashMap<Player, Location> cachedTeleport) {
        for (Map.Entry<Player, Location> playerLocationEntry : cachedTeleport.entrySet()) {
            Player player = playerLocationEntry.getKey();
            if (player.isOnline()) {
                player.teleport(playerLocationEntry.getValue());
            }
        }
    }

    private void restoreRedstone(LinkedHashSet<Location> cachedRedstoneActivity) {
        if (cachedRedstoneActivity.isEmpty()) {
            NeoUtils.getNeoUtilities().getFancyLogger().info("Redstone restore skipped: no cached locations.");
            return;
        }

        int totalLocations = cachedRedstoneActivity.size();
        Deque<Location> restoreQueue = new ArrayDeque<>(cachedRedstoneActivity);
        Deque<Location> pendingUnloaded = new ArrayDeque<>();

        new BukkitRunnable() {
            private int unloadRetryPass = 0;

            @Override
            public void run() {
                int processed = 0;
                while (!restoreQueue.isEmpty() && processed < REDSTONE_RESTORE_BATCH_SIZE) {
                    Location location = restoreQueue.pollFirst();
                    if (location == null) {
                        continue;
                    }
                    if (!wakeBlock(location, pendingUnloaded)) {
                        continue;
                    }
                    processed++;
                }

                if (!restoreQueue.isEmpty()) {
                    return;
                }

                if (!pendingUnloaded.isEmpty() && unloadRetryPass < REDSTONE_RESTORE_MAX_PASSES) {
                    restoreQueue.addAll(pendingUnloaded);
                    pendingUnloaded.clear();
                    unloadRetryPass++;
                    return;
                }

                if (!pendingUnloaded.isEmpty()) {
                    NeoUtils.getNeoUtilities().getFancyLogger().warn(
                            "Redstone restore finished with " + pendingUnloaded.size()
                                    + " unloaded location(s) still pending."
                    );
                }
                NeoUtils.getNeoUtilities().getFancyLogger().info(
                        "Redstone restore complete for " + totalLocations + " cached location(s)."
                );
                cancel();
            }
        }.runTaskTimer(NeoUtils.getInstance(), 0L, 1L);
    }

    private boolean wakeBlock(Location location, Deque<Location> pendingUnloaded) {
        if (!isChunkLoaded(location)) {
            pendingUnloaded.addLast(cloneBlockLocation(location));
            return false;
        }

        try {
            Block block = location.getBlock();
            if (block.isEmpty()) {
                return false;
            }

            for (BlockFace face : DIRECT_NEIGHBORS) {
                Block neighbor = block.getRelative(face);
                if (!neighbor.isEmpty()) {
                    neighbor.getState().update(false, false);
                }
            }
            block.getState().update(true, true);
            for (BlockFace face : DIRECT_NEIGHBORS) {
                Block neighbor = block.getRelative(face);
                if (!neighbor.isEmpty()) {
                    neighbor.getState().update(false, true);
                }
            }
            return true;
        } catch (Exception exception) {
            NeoUtils.getNeoUtilities().getFancyLogger().warn("Failed to restore halted redstone at " + formatLocation(location));
            return false;
        }
    }

    private Location cloneBlockLocation(Location location) {
        World world = location.getWorld();
        if (world == null) {
            return location.clone();
        }
        return new Location(world, location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    private boolean isChunkLoaded(Location location) {
        World world = location.getWorld();
        if (world == null) {
            return false;
        }
        return world.isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4);
    }

    private String formatLocation(Location location) {
        World world = location.getWorld();
        String worldName = world == null ? "unknown" : world.getName();
        return worldName + " [" + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + "]";
    }
}
