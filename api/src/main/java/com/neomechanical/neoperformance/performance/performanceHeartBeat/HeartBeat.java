package com.neomechanical.neoperformance.performance.performanceHeartBeat;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.halt.CachedData;
import com.neomechanical.neoperformance.performance.halt.HaltServer;
import com.neomechanical.neoperformance.performance.haltActions.HaltActions;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.performance.utils.TpsUtils;
import com.neomechanical.neoperformance.version.heartbeat.IHeartBeat;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

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

    public void start() {
        AtomicLong haltStartTime = new AtomicLong(0);
        AtomicBoolean previouslyHalted = new AtomicBoolean(false);
        AtomicBoolean manualHalt = new AtomicBoolean(false);

        new BukkitRunnable() {
            @Override
            public void run() {
                setTPS();

                boolean isCurrentlyHalted = TpsUtils.isServerHalted(getUpdatedTPS(), null, plugin);

                if (isCurrentlyHalted && !previouslyHalted.get()) {
                    manualHalt.set(dataManager.isManualHalt());
                    if (!manualHalt.get()) {
                        HaltNotifier.notifyHalted(dataManager);
                    }
                    HaltActions.runHaltActions(tps);
                    haltStartTime.set(System.currentTimeMillis());
                } else if (!isCurrentlyHalted && previouslyHalted.get()) {
                    if (!manualHalt.get()) {
                        HaltNotifier.notifyResumed(dataManager);
                    }
                    haltStartTime.set(0);
                    restoreServer();
                }

                previouslyHalted.set(isCurrentlyHalted);
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
