package com.neomechanical.neoperformance.performanceOptimiser.utils;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.managers.TweakDataManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import static com.neomechanical.neoperformance.performanceOptimiser.utils.Lag.getRecentTpsRefl;

public interface Tps {
    TweakDataManager tweakDataManager = NeoPerformance.getTweakDataManager();

    default double getTPS() {

        double tps = getRecentTpsRefl()[0];
        if (tps <= 0) { //0 normally means the server is still starting, so we'll just return 20 as a default value as the server can continue to load without interruptions.
            tps = 20;
        }
        return tps;
    }

    default boolean isServerHalted(@Nullable Player player) {
        double tps = getTPS();
        int haltAt = tweakDataManager.getTweakData().getTpsHaltAt();
        if (haltAt == -1) {
            return false;
        }
        if (player != null) {
            if (tweakDataManager.isBypassed(player)) {
                return false;
            }
        }
        if (tps <= 0) { //0 normally means the server is still starting, so we'll just return 20 as a default value as the server can continue to load without interruptions.
            return false;
        }
        if (tweakDataManager.isManualHalt()) {
            return true;
        }
        return getTPS() <= haltAt;
    }

    default String getFancyTps() {
        double tps = getTPS();
        tps = (double) Math.round(tps * 100) / 100;
        if (tps >= 18) {
            return "&a&l" + tps;
        } else if (tps <= 18 && tps >= 15) {
            return "&e&l" + tps;
        } else if (tps <= 15 && tps >= 10) {
            return "&c&l" + tps;
        } else if (tps <= 10 && tps >= 0) {
            return "&4&l" + tps;
        } else {
            return "&4&l" + tps;
        }
    }

    default String fancyIsServerHalted() {
        if (isServerHalted(null)) {
            return "&c&ltrue";
        }
        return "&a&lfalse";
    }

    default int getHaltTps() {
        return NeoPerformance.getTweakDataManager().getTweakData().getTpsHaltAt();
    }

    default String getFancyHaltTps() {
        int haltAt = NeoPerformance.getTweakDataManager().getTweakData().getTpsHaltAt();
        if (haltAt == -1) {
            return "&c&lN/A";
        } else {
            return "&a&l" + haltAt;
        }
    }
}