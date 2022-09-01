package com.neomechanical.neoperformance.performance.halt;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CachedData {
    public List<Location> cachedRedstoneActivity = new ArrayList<>();
    public LinkedHashMap<Player, Location> cachedTeleport = new LinkedHashMap<>();

}
