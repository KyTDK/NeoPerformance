package com.neomechanical.neoperformance.version.restore;

import com.neomechanical.neoperformance.version.heartbeat.IHeartBeat;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.List;

public class HeartBeatWrapperNONLEGACY implements IHeartBeat {

    @Override
    public void restoreServer(LinkedHashMap<Player, Location> cachedTeleport, List<Location> cachedRedstoneActivity) {
        //run teleport cache
        for (Player player : cachedTeleport.keySet()) {
            if (player.isOnline()) {
                player.teleport(cachedTeleport.get(player));
            }
        }
        if (cachedRedstoneActivity.size() > 0) {
            for (Location location : cachedRedstoneActivity) {
                try {
                    Block block = location.getBlock();
                    BlockData data = block.getBlockData();
                    if (data instanceof Powerable) {
                        Powerable powerable = (Powerable) data;
                        powerable.setPowered(powerable.isPowered());
                        block.setBlockData(powerable);
                    } else if (data instanceof AnaloguePowerable) {
                        AnaloguePowerable analoguePowerable = (AnaloguePowerable) data;
                        analoguePowerable.setPower(analoguePowerable.getPower());
                        block.setBlockData(analoguePowerable);
                    }
                    BlockData blockData = block.getBlockData().clone();
                    block.setType(block.getType());
                    block.setBlockData(blockData);
                    block.getState().update(true, true);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
