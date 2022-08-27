package com.neomechanical.neoperformance.version.restore;

import com.neomechanical.neoperformance.performanceOptimiser.halt.CachedData;
import com.neomechanical.neoperformance.performanceOptimiser.managers.DataManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.entity.Player;

public class WrapperNONLEGACY implements HeartBeatWrapper {

    @Override
    public void restoreServer(CachedData cachedData, DataManager dataManager) {
        //run teleport cache
        for (Player player : cachedData.cachedTeleport.keySet()) {
            if (player.isOnline()) {
                player.teleport(cachedData.cachedTeleport.get(player));
            }
        }
        if (cachedData.cachedRedstoneActivity.size() > 0) {
            dataManager.setRestoringRedstone(true);
            for (Location location : cachedData.cachedRedstoneActivity) {
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
            dataManager.setRestoringRedstone(false);
            //Clear the cache
            cachedData.cachedRedstoneActivity.clear();
            cachedData.cachedTeleport.clear();
        }
    }
}
