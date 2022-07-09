package com.neomechanical.neoperformance.performanceOptimiser.performanceHeartBeat;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.performanceOptimiser.halt.CachedData;
import com.neomechanical.neoperformance.performanceOptimiser.halt.HaltServer;
import com.neomechanical.neoperformance.performanceOptimiser.utils.Tps;
import com.neomechanical.neoperformance.utils.Logger;
import com.neomechanical.neoperformance.utils.MessageUtil;
import com.neomechanical.neoperformance.utils.mail.EmailClient;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HeartBeat implements Tps, PerformanceConfigurationSettings {
    private final CachedData cachedData = HaltServer.cachedData;
    public void start() {
        final long[] haltStartTime = new long[1];
        boolean notifyAdmin = getTweakData().getNotifyAdmin();
        boolean broadcastAll = getTweakData().getBroadcastHalt();
        final boolean[] halted = {false};
        final boolean[] manualHalt = {false};
        new BukkitRunnable() {
            @Override
            public void run() {
                if (isServerHalted(null)) {
                    manualHalt[0] = tweakDataManager.isManualHalt();
                    if (!halted[0] && !manualHalt[0]) {
                        haltStartTime[0] = System.currentTimeMillis();
                        if (getTweakData().getUseMailServer()) {
                            EmailClient emailClient = new EmailClient();
                            emailClient.sendAsHtml("TPS is too low, halting server", "The server has halted because the TPS is too low.\n" +
                                    "The TPS is currently at " + getTPS() + " and the TPS threshold is " + getHaltTps() + ".\n" +
                                    "The server will restart in " + "10" + " minutes if it doesn't recover.");
                        }
                        String message = "&cTPS is too low, halting server";
                        if (broadcastAll) {
                            Bukkit.broadcastMessage(MessageUtil.color(message));
                        } else if (notifyAdmin) {
                            MessageUtil.messageAdmins(message);
                        }
                    }
                    halted[0] = true;
                    if (!manualHalt[0] && (System.currentTimeMillis() - haltStartTime[0] >= 1000 * 60 * 10)) {
                        //after 10 minutes of the server being halted, reboot the server
                        NeoPerformance.getInstance().getServer().shutdown();
                    }
                }
                else {
                    if (halted[0]) {
                        if (!manualHalt[0]) {
                            String message = "&aTPS is back to normal, recovering server";
                            if (broadcastAll) {
                                Bukkit.broadcastMessage(MessageUtil.color(message));
                            } else if (notifyAdmin) {
                                MessageUtil.messageAdmins(message);
                            }
                        }
                        halted[0] = false;
                        haltStartTime[0] = 0;
                        //run teleport cache
                        for (Player player : cachedData.cachedTeleport.keySet()) {
                            if (player.isOnline()) {
                                player.teleport(cachedData.cachedTeleport.get(player));
                            }
                        }
                        for (Block block : cachedData.cachedRedstoneActivity.keySet()) {
                            try {
                                org.bukkit.block.data.BlockData data = block.getBlockData();
                                if (!(data instanceof AnaloguePowerable powerable))
                                    continue; // Ignore any non-powerable blocks
                                powerable.setPower(cachedData.cachedRedstoneActivity.get(block));
                                block.setBlockData(powerable);
                            } catch (NoClassDefFoundError e) {
                                Logger.outdated();
                            }
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
