package com.neomechanical.neoperformance.version.restore;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import com.neomechanical.neoperformance.version.heartbeat.IHeartBeat;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class HeartBeatWrapperLEGACY implements IHeartBeat {

    @Override
    public void restoreServer(LinkedHashMap<Player, Location> cachedTeleport, LinkedHashMap<Location, BlockState> cachedRedstoneActivity) {
        //run teleport cache
        for (Player player : cachedTeleport.keySet()) {
            if (player.isOnline()) {
                player.teleport(cachedTeleport.get(player));
            }
        }
        if (cachedRedstoneActivity.size() > 0) {
            NeoUtils.getNeoUtilities().getFancyLogger().warn("Redstone restoration was attempted, however, this feature isn't supported on your minecraft version");
        }
    }
}
