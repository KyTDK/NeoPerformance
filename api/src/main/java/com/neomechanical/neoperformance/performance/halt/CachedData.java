package com.neomechanical.neoperformance.performance.halt;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class CachedData {
    private final LinkedHashSet<Location> cachedRedstoneActivity = new LinkedHashSet<>();
    private final LinkedHashMap<Player, Location> cachedTeleport = new LinkedHashMap<>();

    public void cacheTeleport(Player player, Location destination) {
        if (destination == null) {
            return;
        }
        cachedTeleport.putIfAbsent(player, destination.clone());
    }

    public void cacheRedstoneLocation(Location location) {
        if (location == null || location.getWorld() == null) {
            return;
        }
        cachedRedstoneActivity.add(new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    public LinkedHashMap<Player, Location> snapshotTeleportCache() {
        return new LinkedHashMap<>(cachedTeleport);
    }

    public LinkedHashSet<Location> snapshotRedstoneActivity() {
        return new LinkedHashSet<>(cachedRedstoneActivity);
    }

    public void clear() {
        cachedRedstoneActivity.clear();
        cachedTeleport.clear();
    }
}
