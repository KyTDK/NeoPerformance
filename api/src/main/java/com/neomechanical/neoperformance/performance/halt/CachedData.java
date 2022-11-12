package com.neomechanical.neoperformance.performance.halt;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class CachedData {
    public LinkedHashSet<Location> cachedRedstoneActivity = new LinkedHashSet<>();
    public LinkedHashMap<Player, Location> cachedTeleport = new LinkedHashMap<>();

}
