package com.neomechanical.neoperformance.performanceOptimiser.performanceHeartBeat;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.performanceOptimiser.halt.CachedData;
import com.neomechanical.neoperformance.performanceOptimiser.halt.HaltServer;
import com.neomechanical.neoperformance.performanceOptimiser.utils.Tps;
import com.neomechanical.neoperformance.utils.Logger;
import com.neomechanical.neoperformance.utils.mail.EmailClient;
import com.neomechanical.neoutils.messages.MessageUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static com.neomechanical.neoperformance.performanceOptimiser.utils.tps.TPSReflection.getRecentTpsRefl;

public class HeartBeat implements Tps, PerformanceConfigurationSettings {
    private final CachedData cachedData = HaltServer.cachedData;
    private final NeoPerformance plugin = NeoPerformance.getInstance();
    private final MessageUtil messageUtil = new MessageUtil(NeoPerformance.adventure());
    private static double tps;

    public static double getUpdatedTPS() {
        if (tps <= 0) { //0 normally means the server is still starting, so we'll just return 20 as a default value as the server can continue to load without interruptions.
            tps = 20;
        }
        return tps;
    }

    public void start() {
        final long[] haltStartTime = new long[1];
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
                        //A manual halt doesn't constitute emails or notifications
                        if (!manualHalt[0]) {
                            haltStartTime[0] = System.currentTimeMillis();
                            if (getMailData().getUseMailServer()) {
                                EmailClient emailClient = new EmailClient();
                                //Is run asynchronously
                                emailClient.sendAsHtml(plugin.getLanguageManager().getString("email_notifications.subject", null),
                                        plugin.getLanguageManager().getString("email_notifications.body", null));
                            }
                            String message = plugin.getLanguageManager().getString("notify.serverHalted", null);
                            if (getTweakData().getBroadcastHalt()) {
                                messageUtil.sendMMAll(message);
                            } else if (getTweakData().getNotifyAdmin()) {
                                messageUtil.sendMMAdmins(message);
                            }
                        }
                    }
                    halted[0] = true;
                    if (!manualHalt[0] && (System.currentTimeMillis() - haltStartTime[0] >= 1000L * getHaltData().getHaltTimeout())) {
                        //after 10 minutes of the server being halted, reboot the server
                        NeoPerformance.getInstance().getServer().shutdown();
                    }
                } else if (halted[0]) {
                    //Again, a manual halt doesn't require notifications
                    if (!manualHalt[0]) {
                        String message = plugin.getLanguageManager().getString("notify.serverResumed", null);
                        if (getTweakData().getBroadcastHalt()) {
                            messageUtil.sendMMAll(message);
                        } else if (getTweakData().getNotifyAdmin()) {
                            messageUtil.sendMMAdmins(message);
                        }
                    }
                    halted[0] = false;
                    haltStartTime[0] = 0;
                    restoreServer();
                }

            }
        }.runTaskTimer(NeoPerformance.getInstance(), 0, getTweakData().getHeartBeatRate());
    }

    private void restoreServer() {
        //run teleport cache
        for (Player player : cachedData.cachedTeleport.keySet()) {
            if (player.isOnline()) {
                player.teleport(cachedData.cachedTeleport.get(player));
            }
        }
        if (cachedData.cachedRedstoneActivity.size() > 0) {
            DATA_MANAGER.setRestoringRedstone(true);
            for (Location location : cachedData.cachedRedstoneActivity) {
                try {
                    Block block = location.getBlock();
                    org.bukkit.block.data.BlockData data = block.getBlockData();
                    if (data instanceof Powerable powerable) {
                        powerable.setPowered(powerable.isPowered());
                        block.setBlockData(powerable);
                    } else if (data instanceof AnaloguePowerable analoguePowerable) {
                        analoguePowerable.setPower(analoguePowerable.getPower());
                        block.setBlockData(analoguePowerable);
                    }
                    BlockData blockData = block.getBlockData().clone();
                    block.setType(block.getType());
                    block.setBlockData(blockData);
                    block.getState().update(true, true);
                } catch (NoClassDefFoundError e) {
                    Logger.outdated();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            DATA_MANAGER.setRestoringRedstone(false);
            //Clear the cache
            cachedData.cachedRedstoneActivity.clear();
            cachedData.cachedTeleport.clear();
        }
    }

    private void setTPS() {
        tps = getRecentTpsRefl()[0];
    }
}
