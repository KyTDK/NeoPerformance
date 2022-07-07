package com.neomechanical.neoperformance.performanceOptimiser.halt;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CachedData {
    public HashMap<Block, Integer> cachedRedstoneActivity = new HashMap<>();
    public HashMap<Player, Location> cachedTeleport = new HashMap<>();

}
