package com.neomechanical.neoperformance.performance.performanceHeartBeat;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.halt.CachedData;
import com.neomechanical.neoperformance.performance.halt.HaltServer;
import com.neomechanical.neoperformance.performance.haltActions.HaltActions;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.performance.utils.TpsUtils;
import com.neomechanical.neoperformance.utils.mail.EmailClient;
import com.neomechanical.neoperformance.version.heartbeat.IHeartBeat;
import com.neomechanical.neoutils.messages.MessageUtil;
import org.bukkit.scheduler.BukkitRunnable;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;
import static com.neomechanical.neoperformance.performance.utils.tps.TPSReflection.getRecentTpsRefl;

public class HeartBeat {
    private final CachedData cachedData = HaltServer.cachedData;
    private final NeoPerformance plugin;
    private final DataManager dataManager;
    private final IHeartBeat iHeartBeat;
    private double tps;

    public HeartBeat(NeoPerformance plugin, DataManager dataManager, IHeartBeat iHeartBeat) {
        this.plugin = plugin;
        this.dataManager = dataManager;
        this.iHeartBeat = iHeartBeat;
    }

    public double getUpdatedTPS() {
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
                //if the server is halted
                if (TpsUtils.isServerHalted(getUpdatedTPS(), null, plugin)) {
                    manualHalt[0] = dataManager.isManualHalt();
                    if (!halted[0]) {
                        //Check if it's NOT a manual halt
                        //A manual halt doesn't constitute emails or notifications
                        if (!manualHalt[0]) {
                            //Run halt actions
                            HaltActions.runHaltActions(tps);
                            //Set halt time
                            haltStartTime[0] = System.currentTimeMillis();
                            if (dataManager.getMailData().getUseMailServer()) {
                                EmailClient emailClient = new EmailClient(dataManager);
                                //Is run asynchronously
                                emailClient.sendAsHtml(getLanguageManager().getString("email_notifications.subject", null),
                                        getLanguageManager().getString("email_notifications.body", null));
                            }
                            String message = getLanguageManager().getString("notify.serverHalted", null);
                            if (dataManager.getTweakData().getBroadcastHalt()) {
                                MessageUtil.sendMMAll(message);
                            } else if (dataManager.getTweakData().getNotifyAdmin()) {
                                MessageUtil.sendMMAdmins(message);
                            }
                        }
                    }
                    halted[0] = true;
                    if (!manualHalt[0] && (System.currentTimeMillis() - haltStartTime[0] >= 1000L * dataManager.getHaltData().getHaltTimeout())) {
                        //after 10 minutes of the server being halted, reboot the server
                        plugin.getServer().shutdown();
                    }
                } else if (halted[0]) {
                    //Again, a manual halt doesn't require notifications
                    if (!manualHalt[0]) {
                        String message = getLanguageManager().getString("notify.serverResumed", null);
                        if (dataManager.getTweakData().getBroadcastHalt()) {
                            MessageUtil.sendMMAll(message);
                        } else if (dataManager.getTweakData().getNotifyAdmin()) {
                            MessageUtil.sendMMAdmins(message);
                        }
                    }
                    halted[0] = false;
                    haltStartTime[0] = 0;
                    iHeartBeat.restoreServer(cachedData.cachedTeleport, cachedData.cachedRedstoneActivity);
                }

            }
        }.runTaskTimer(plugin, 0, dataManager.getTweakData().getHeartBeatRate());
    }
    private void setTPS() {
        tps = getRecentTpsRefl()[0];
    }
}
