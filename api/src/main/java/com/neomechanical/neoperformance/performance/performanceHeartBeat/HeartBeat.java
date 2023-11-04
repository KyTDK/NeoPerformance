package com.neomechanical.neoperformance.performance.performanceHeartBeat;

import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.halt.CachedData;
import com.neomechanical.neoperformance.performance.halt.HaltServer;
import com.neomechanical.neoperformance.performance.haltActions.HaltActions;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.performance.utils.TpsUtils;
import com.neomechanical.neoperformance.version.heartbeat.IHeartBeat;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedHashMap;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;
import static com.neomechanical.neoperformance.performance.utils.tps.TPSReflection.getRecentTpsRefl;

public class HeartBeat {
    private final CachedData cachedData = HaltServer.cachedData;
    private final NeoPerformance plugin;
    private final DataManager dataManager;
    public final IHeartBeat iHeartBeatMap;
    private double tps;

    public HeartBeat(NeoPerformance plugin, DataManager dataManager, IHeartBeat iHeartBeatMap) {
        this.plugin = plugin;
        this.dataManager = dataManager;
        this.iHeartBeatMap = iHeartBeatMap;
    }
    public double getUpdatedTPS() {
        if (tps <= 0) { //0 normally means the server is still starting, so we'll just return 20 as a default value as the server can continue to load without interruptions.
            tps = 20;
        }
        return tps;
    }

    private void onHalt(boolean[] manualHalt, long[] haltStartTime, boolean[] halted) {
        manualHalt[0] = dataManager.isManualHalt();
        if (!manualHalt[0]) { //If not manual halt, then notify
            HaltNotifier.notify(dataManager);
        }
        //Run halt actions
        HaltActions.runHaltActions(tps);
        //Set halt time
        haltStartTime[0] = System.currentTimeMillis();
        halted[0] = true;
        if (!manualHalt[0] && (System.currentTimeMillis() - haltStartTime[0] >= 1000L * dataManager.getHaltData().getHaltTimeout())) {
            //after 10 minutes of the server being halted, reboot the server
            plugin.getServer().shutdown();
        }
    }

    private void onResume(boolean[] manualHalt, long[] haltStartTime, boolean[] halted) {
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
        restoreServer();
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
                if (TpsUtils.isServerHalted(getUpdatedTPS(), null, plugin) && !halted[0]) {//If server is halted and was previously not halted
                    onHalt(manualHalt, haltStartTime, halted);
                } else if (halted[0]) {
                    onResume(manualHalt, haltStartTime, halted);
                }
            }
        }.runTaskTimer(plugin, 0, dataManager.getTweakData().getHeartBeatRate());
    }

    public void restoreServer() {
        iHeartBeatMap.restoreServer(new LinkedHashMap<>(cachedData.cachedTeleport), new LinkedHashMap<>(cachedData.cachedRedstoneActivity));
        HaltServer.cachedData.cachedRedstoneActivity.clear();
        HaltServer.cachedData.cachedTeleport.clear();
    }

    private void setTPS() {
        tps = getRecentTpsRefl()[0];
    }
}
