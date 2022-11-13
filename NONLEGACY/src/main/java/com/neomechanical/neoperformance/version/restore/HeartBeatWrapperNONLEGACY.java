package com.neomechanical.neoperformance.version.restore;

import com.google.common.collect.Lists;
import com.neomechanical.neoconfig.neoutils.NeoUtils;
import com.neomechanical.neoperformance.version.heartbeat.IHeartBeat;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class HeartBeatWrapperNONLEGACY implements IHeartBeat {

    @Override
    public void restoreServer(LinkedHashMap<Player, Location> cachedTeleport, List<Location> cachedRedstoneActivity, Runnable afterFunction) {
        //run teleport cache
        for (Map.Entry<Player, Location> playerLocationEntry : cachedTeleport.entrySet()) {
            Player player = playerLocationEntry.getKey();
            if (player.isOnline()) {
                player.teleport(playerLocationEntry.getValue());
            }
        }
        //Chunk redstone restoration
        Stack<List<Location>> redstoneStack = new Stack<>();
        redstoneStack.addAll(Lists.partition(cachedRedstoneActivity, 100));
        new BukkitRunnable() {
            @Override
            public void run() {
                if (redstoneStack.isEmpty()) {
                    afterFunction.run();
                    cancel();
                    return;
                }
                List<Location> batch = redstoneStack.pop();
                for (Location location : batch) {
                    try {
                        Block block = location.getBlock();
                        if (!block.getChunk().isLoaded()) {
                            continue;
                        }
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
                        //Update block
                        block.setType(block.getType());
                        block.setBlockData(blockData);
                        block.getState().update(true, true);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.runTaskTimer(NeoUtils.getInstance(), 0, 1);
    }
}
