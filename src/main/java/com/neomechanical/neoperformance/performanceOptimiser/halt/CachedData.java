package com.neomechanical.neoperformance.performanceOptimiser.halt;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class CachedData {
    public LinkedHashMap<Block, Integer> cachedRedstoneActivity = new LinkedHashMap<>();
    public LinkedHashMap<Player, Location> cachedTeleport = new LinkedHashMap<>();

}
