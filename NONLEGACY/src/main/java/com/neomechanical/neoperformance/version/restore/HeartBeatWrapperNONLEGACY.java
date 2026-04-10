package com.neomechanical.neoperformance.version.restore;

import com.neomechanical.neoutils.NeoUtils;
import com.neomechanical.neoperformance.version.heartbeat.IHeartBeat;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class HeartBeatWrapperNONLEGACY implements IHeartBeat {
    private static final int REDSTONE_RESTORE_BATCH_SIZE = 64;

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
            return;
        }

        Deque<Location> restoreQueue = new ArrayDeque<>(cachedRedstoneActivity);
        new BukkitRunnable() {
            @Override
            public void run() {
                int processed = 0;
                while (!restoreQueue.isEmpty() && processed < REDSTONE_RESTORE_BATCH_SIZE) {
                    Location location = restoreQueue.pollFirst();
                    if (location != null) {
                        wakeBlock(location);
                    }
                    processed++;
                }

                if (restoreQueue.isEmpty()) {
                    cancel();
                }
            }
        }.runTaskTimer(NeoUtils.getInstance(), 0L, 1L);
    }

    private void wakeBlock(Location location) {
        if (!isChunkLoaded(location)) {
            return;
        }

        try {
            Block block = location.getBlock();
            if (block.isEmpty()) {
                return;
            }

            block.setBlockData(block.getBlockData().clone(), true);
            block.getState().update(true, true);
        } catch (Exception exception) {
            NeoUtils.getNeoUtilities().getFancyLogger().warn("Failed to restore halted redstone at " + formatLocation(location));
        }
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
