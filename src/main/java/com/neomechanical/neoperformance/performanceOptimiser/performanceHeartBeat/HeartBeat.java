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
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

import static com.neomechanical.neoperformance.performanceOptimiser.utils.tps.TPSReflection.getRecentTpsRefl;

public class HeartBeat implements Tps, PerformanceConfigurationSettings {
    private final CachedData cachedData = HaltServer.cachedData;
    private final NeoPerformance plugin = NeoPerformance.getInstance();
    private static double tps;

    public static double getUpdatedTPS() {
        if (tps <= 0) { //0 normally means the server is still starting, so we'll just return 20 as a default value as the server can continue to load without interruptions.
            tps = 20;
        }
        return tps;
    }

    public void start() {
        final long[] haltStartTime = new long[1];
        boolean notifyAdmin = getTweakData().getNotifyAdmin();
        boolean broadcastAll = getTweakData().getBroadcastHalt();
        final boolean[] halted = {false};
        final boolean[] manualHalt = {false};
        new BukkitRunnable() {
            @Override
            public void run() {
                //set the tps every second
                setTPS();
                //check if the server is halted
                if (isServerHalted(null)) {
                    manualHalt[0] = DATA_MANAGER.isManualHalt();
                    if (!halted[0]) {
                        if (!manualHalt[0]) {
                            haltStartTime[0] = System.currentTimeMillis();
                            if (getMailData().getUseMailServer()) {
                                EmailClient emailClient = new EmailClient();
                                emailClient.sendAsHtml(plugin.getLanguageManager().getString("email_notifications.subject", null),
                                        plugin.getLanguageManager().getString("email_notifications.body", null));
                            }
                            String message = plugin.getLanguageManager().getString("notify.serverHalted", null);
                            if (broadcastAll) {
                                MessageUtil.sendMMAll(message);
                            } else if (notifyAdmin) {
                                MessageUtil.sendMMAdmins(message);
                            }
                        }
                    }
                    halted[0] = true;
                    if (!manualHalt[0] && (System.currentTimeMillis() - haltStartTime[0] >= 1000L * getHaltData().getHaltTimeout())) {
                        //after 10 minutes of the server being halted, reboot the server
                        NeoPerformance.getInstance().getServer().shutdown();
                    }
                } else if (halted[0]) {
                    if (!manualHalt[0]) {
                        String message = plugin.getLanguageManager().getString("notify.serverResumed", null);
                        if (broadcastAll) {
                            MessageUtil.sendMMAll(message);
                        } else if (notifyAdmin) {
                            MessageUtil.sendMMAdmins(message);
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
                    Map<Integer, String> inverseMap = new HashMap<>();
                    for (Map.Entry<String, Integer> entry : cachedData.cachedRedstoneActivity.entrySet()) {
                        inverseMap.put(entry.getValue(), entry.getKey());
                    }
                    for (BlockState blockState : cachedData.cachedRedstoneActivity.keySet()) {
                        try {
                            org.bukkit.block.data.BlockData data = blockState.getBlockData();
                            if (data instanceof org.bukkit.block.data.Powerable) {
                                blockState.getBlock().setBlockData(data);
                                blockState.getBlock().getState().update(true, false);
                                Bukkit.broadcastMessage("" + cachedData.cachedRedstoneActivity.get(blockState));
                            } else if (data instanceof org.bukkit.block.data.AnaloguePowerable powerable) {
                                powerable.setPower(cachedData.cachedRedstoneActivity.get(blockState));
                                blockState.setBlockData(powerable);
                            }
                        } catch (NoClassDefFoundError e) {
                            Logger.outdated();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    //clear cache entirely
                    cachedData.cachedTeleport.clear();
                    cachedData.cachedRedstoneActivity.clear();
                }

            }
        }.runTaskTimer(NeoPerformance.getInstance(), 0, getTweakData().getHeartBeatRate());
    }

    private void setTPS() {
        tps = getRecentTpsRefl()[0];
    }
}
