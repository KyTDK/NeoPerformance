package com.neomechanical.neoperformance.version.restore;

import com.neomechanical.neoperformance.performanceOptimiser.halt.CachedData;
import com.neomechanical.neoperformance.performanceOptimiser.managers.DataManager;
import com.neomechanical.neoutils.NeoUtils;
import org.bukkit.entity.Player;

public class WrapperLEGACY implements HeartBeatWrapper {
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
            NeoUtils.getInstance().getFancyLogger().warn("Redstone restoration was attempted, however, this feature isn't supported on your minecraft version");
            dataManager.setRestoringRedstone(false);
            //Clear the cache
            cachedData.cachedRedstoneActivity.clear();
            cachedData.cachedTeleport.clear();
        }
    }
}
