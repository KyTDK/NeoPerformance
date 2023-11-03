package com.neomechanical.neoperformance.version.heartbeat;

import com.neomechanical.neoconfig.neoutils.version.VersionWrapper;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public interface IHeartBeat extends VersionWrapper {
    void restoreServer(LinkedHashMap<Player, Location> cachedTeleport, LinkedHashMap<Location, BlockState> cachedRedstoneActivity);
}
