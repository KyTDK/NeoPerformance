package com.neomechanical.neoperformance.performanceOptimiser.utils;

import net.minecraft.server.MinecraftServer;

public class Tps {
    public double getTPS() {
        double tps = MinecraftServer.getServer().recentTps[0];
        if (tps <= 0) { //0 normally means the server is still starting, so we'll just return 20 as a default value as the server can continue to load without interruptions.
            tps = 20;
        }
        return tps;
    }
}