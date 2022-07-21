package com.neomechanical.neoperformance.performanceOptimiser.halt;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CachedData {
    public HashMap<Block, Integer> cachedRedstoneActivity = new HashMap<>();
    public LinkedHashMap<Player, Location> cachedTeleport = new LinkedHashMap<>();

}
