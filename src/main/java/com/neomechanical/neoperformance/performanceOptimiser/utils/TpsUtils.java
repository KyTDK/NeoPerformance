package com.neomechanical.neoperformance.performanceOptimiser.utils;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.utils.updates.UpdateChecker;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class TpsUtils {
    private TpsUtils() {

    }

    public static double getTPS(NeoPerformance plugin) {
        return plugin.getHeartBeat().getUpdatedTPS();
    }

    public static boolean isServerHalted(double tps, @Nullable Player player, NeoPerformance plugin) {
        if (tps == 0) {
            return false;
        }
        int haltAt = plugin.getDataManager().getTweakData().getTpsHaltAt();
        if (haltAt == -1) {
            return false;
        }
        if (player != null) {
            if (plugin.getDataManager().isBypassed(player)) {
                return false;
            }
        }
        if (plugin.getDataManager().isManualHalt()) {
            return true;
        }
        return tps <= haltAt;
    }

    public static String getFancyTps(NeoPerformance plugin) {
        double tps = getTPS(plugin);
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

    public static String fancyIsServerHalted(double tps, NeoPerformance plugin) {
        if (isServerHalted(tps, null, plugin)) {
            return "<red><bold>true";
        }
        return "<green><bold>false";
    }

    public static String getFancyHaltTps(NeoPerformance plugin) {
        int haltAt = plugin.getDataManager().getTweakData().getTpsHaltAt();
        if (haltAt == -1) {
            return "<red><bold>N/A";
        } else {
            return "<green><bold>" + haltAt;
        }
    }

    public static String getFancyUpdateStatus() {
        Boolean isUpToDate = UpdateChecker.UpToDate;
        if (isUpToDate == null) {
            return "<red><bold>N/A";
        } else if (isUpToDate) {
            return "<green><bold>true";
        } else {
            return "<red><bold>false";
        }
    }
}