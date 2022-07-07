package com.neomechanical.neoperformance.performanceOptimiser.performanceHeartBeat;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.halt.CachedData;
import com.neomechanical.neoperformance.performanceOptimiser.halt.HaltServer;
import com.neomechanical.neoperformance.performanceOptimiser.managers.TweakDataManager;
import com.neomechanical.neoperformance.performanceOptimiser.utils.Tps;
import com.neomechanical.neoperformance.utils.MessageUtil;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HeartBeat {
    private final Tps tps = new Tps();
    private final CachedData cachedData = HaltServer.cachedData;
    public void start(TweakDataManager tweakDataManager) {
        int tpsHalt = tweakDataManager.getTweakData().getTpsHaltAt();
        final long[] haltStartTime = new long[1];
        boolean notifyAdmin = tweakDataManager.getTweakData().getNotifyAdmin();
        final boolean[] halted = {false};
        new BukkitRunnable() {
            @Override
            public void run() {
                if (tps.getTPS() <= tpsHalt) {
                    if (notifyAdmin&&!halted[0]) {
                        halted[0] = true;
                        haltStartTime[0] = System.currentTimeMillis();
                        MessageUtil.messageAdmins("&cTPS is too low, halting server");
                    }
                    halted[0] = true;
                    if (System.currentTimeMillis() - haltStartTime[0] >= 1000 * 60 * 10) {
                        //after 10 minutes of the server being halted, reboot the server
                        NeoPerformance.getInstance().getServer().shutdown();
                    }
                }
                else {
                    if (halted[0]) {
                        if (notifyAdmin) {
                            MessageUtil.messageAdmins("&aTPS is back to normal, recovering server");
                        }
                        halted[0] = false;
                        haltStartTime[0] = 0;
                        //run teleport cache
                        for (Player player : cachedData.cachedTeleport.keySet()) {
                            if(player.isOnline()) {
                                player.teleport(cachedData.cachedTeleport.get(player));
                            }
                        }
                        for (Block block : cachedData.cachedRedstoneActivity.keySet()) {
                                BlockData data = block.getBlockData();
                                if (!(data instanceof AnaloguePowerable powerable)) continue; // Ignore any non-powerable blocks
                                powerable.setPower(cachedData.cachedRedstoneActivity.get(block));
                                block.setBlockData(powerable);
                        }
                        //clear cache entirely
                        cachedData.cachedTeleport.clear();
                        cachedData.cachedRedstoneActivity.clear();
                      }
                    }
                }
        }.runTaskTimer(NeoPerformance.getInstance(), 0 , 20);
    }
}
