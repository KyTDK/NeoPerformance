package com.neomechanical.neoperformance.version.heartbeat;

import com.neomechanical.neoconfig.neoutils.version.VersionWrapper;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.List;

public interface IHeartBeat extends VersionWrapper {
    void restoreServer(LinkedHashMap<Player, Location> cachedTeleport, List<Location> cachedRedstoneActivity);
}
