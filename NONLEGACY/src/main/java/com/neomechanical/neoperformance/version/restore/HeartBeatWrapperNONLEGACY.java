package com.neomechanical.neoperformance.version.restore;

import com.google.common.collect.Lists;
import com.neomechanical.neoconfig.neoutils.NeoUtils;
import com.neomechanical.neoperformance.version.heartbeat.IHeartBeat;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class HeartBeatWrapperNONLEGACY implements IHeartBeat {
    @Override
    public void restoreServer(LinkedHashMap<Player, Location> cachedTeleport, LinkedHashMap<Location, BlockState> cachedRedstoneActivity) {
        //run teleport cache
        for (Map.Entry<Player, Location> playerLocationEntry : cachedTeleport.entrySet()) {
            Player player = playerLocationEntry.getKey();
            if (player.isOnline()) {
                player.teleport(playerLocationEntry.getValue());
            }
        }
        //Chunk redstone restoration
        Stack<List<Map.Entry<Location, BlockState>>> redstoneStack = new Stack<>();
        redstoneStack.addAll(Lists.partition(new ArrayList<>(cachedRedstoneActivity.entrySet()), 100));
        new BukkitRunnable() {
            @Override
            public void run() {
                if (redstoneStack.isEmpty()) {
                    cancel();
                    return;
                }
                List<Map.Entry<Location, BlockState>> batch = redstoneStack.pop();
                for (Map.Entry<Location, BlockState> entry : batch) {
                    try {
                        Block block = entry.getKey().getBlock();
                        BlockState blockState = entry.getValue();
                        if (!block.getChunk().isLoaded()) {
                            continue;
                        }
                        BlockData blockData = blockState.getBlockData().clone();
                        //Update block
                        block.setType(blockState.getType());
                        block.setBlockData(blockData);
                        blockState.update(true, true);
                        //or
                        //block.getState().update(true, true);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.runTaskTimer(NeoUtils.getInstance(), 0, 1);
    }
}

