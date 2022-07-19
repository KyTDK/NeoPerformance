package com.neomechanical.neoperformance.performanceOptimiser.halt;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class CachedData {
    public LinkedHashMap<BlockState, Integer> cachedRedstoneActivity = new LinkedHashMap<>();
    public LinkedHashMap<Player, Location> cachedTeleport = new LinkedHashMap<>();

}
