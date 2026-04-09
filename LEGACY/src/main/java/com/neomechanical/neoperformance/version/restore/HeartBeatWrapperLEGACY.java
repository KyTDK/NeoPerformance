package com.neomechanical.neoperformance.version.restore;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import com.neomechanical.neoperformance.version.heartbeat.IHeartBeat;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class HeartBeatWrapperLEGACY implements IHeartBeat {

    @Override
    public void restoreServer(LinkedHashMap<Player, Location> cachedTeleport, LinkedHashSet<Location> cachedRedstoneActivity) {
        //run teleport cache
        for (Player player : cachedTeleport.keySet()) {
            if (player.isOnline()) {
                player.teleport(cachedTeleport.get(player));
            }
        }
        if (!cachedRedstoneActivity.isEmpty()) {
            NeoUtils.getNeoUtilities().getFancyLogger().warn("Redstone restoration was attempted, however, this feature isn't supported on your minecraft version");
        }
    }
}
