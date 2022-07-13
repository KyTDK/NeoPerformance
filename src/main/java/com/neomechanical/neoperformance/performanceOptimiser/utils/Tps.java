package com.neomechanical.neoperformance.performanceOptimiser.utils;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.managers.DataManager;
import com.neomechanical.neoperformance.performanceOptimiser.performanceHeartBeat.HeartBeat;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;


public interface Tps {
    DataManager DATA_MANAGER = NeoPerformance.getDataManager();
    default double getTPS() {
        return HeartBeat.getUpdatedTPS();
    }
    default boolean isServerHalted(@Nullable Player player) {
        double tps = getTPS();
        int haltAt = DATA_MANAGER.getTweakData().getTpsHaltAt();
        if (haltAt == -1) {
            return false;
        }
        if (player != null) {
            if (DATA_MANAGER.isBypassed(player)) {
                return false;
            }
        }
        if (tps <= 0) { //0 normally means the server is still starting, so we'll just return 20 as a default value as the server can continue to load without interruptions.
            return false;
        }
        if (DATA_MANAGER.isManualHalt()) {
            return true;
        }
        return getTPS() <= haltAt;
    }

    default String getFancyTps() {
        double tps = getTPS();
        tps = (double) Math.round(tps * 100) / 100;
        if (tps >= 18) {
            return "<green><bold>" + tps;
        } else if (tps <= 18 && tps >= 15) {
            return "<yellow><bold>" + tps;
        } else if (tps <= 15 && tps >= 10) {
            return "<red><bold>" + tps;
        } else if (tps <= 10 && tps >= 0) {
            return "<dark_red><bold>" + tps;
        } else {
            return "<dark_red><bold>" + tps;
        }
    }

    default String fancyIsServerHalted() {
        if (isServerHalted(null)) {
            return "<red><bold>true";
        }
        return "<green><bold>false";
    }

    default int getHaltTps() {
        return NeoPerformance.getDataManager().getTweakData().getTpsHaltAt();
    }

    default String getFancyHaltTps() {
        int haltAt = NeoPerformance.getDataManager().getTweakData().getTpsHaltAt();
        if (haltAt == -1) {
            return "<red><bold>N/A";
        } else {
            return "<green><bold>" + haltAt;
        }
    }
}