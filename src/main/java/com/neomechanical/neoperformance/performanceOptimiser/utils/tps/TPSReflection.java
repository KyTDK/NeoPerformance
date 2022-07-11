package com.neomechanical.neoperformance.performanceOptimiser.utils.tps;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Field;

public class TPSReflection {
    private static Object minecraftServer;
    private static Field recentTps;

    public static double[] getRecentTpsRefl() {
        try {
            if (minecraftServer == null) {
                Server server = Bukkit.getServer();
                Field consoleField = server.getClass().getDeclaredField("console");
                consoleField.setAccessible(true);
                minecraftServer = consoleField.get(server);
            }
            if (recentTps == null) {
                recentTps = minecraftServer.getClass().getSuperclass().getDeclaredField("recentTps");
                recentTps.setAccessible(true);
            }
            return (double[]) recentTps.get(minecraftServer);
        } catch (Exception e) {
            e.printStackTrace();
            return new double[]{0};
        }

    }

}