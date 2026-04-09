package com.neomechanical.neoperformance.performance.utils.tps;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;

public class TPSReflection {
    private static Object minecraftServer;
    private static Field recentTps;
    private static Method serverGetTpsMethod;
    private static boolean warnedRecentTpsUnavailable;
    private static final double[] DEFAULT_TPS = new double[]{20D};

    public static double[] getRecentTpsRefl() {
        try {
            // Modern Paper exposes getTPS() directly on the server object.
            if (serverGetTpsMethod == null) {
                try {
                    serverGetTpsMethod = Bukkit.getServer().getClass().getMethod("getTPS");
                    serverGetTpsMethod.setAccessible(true);
                } catch (NoSuchMethodException ignored) {
                    serverGetTpsMethod = null;
                }
            }
            if (serverGetTpsMethod != null) {
                Object tpsValues = serverGetTpsMethod.invoke(Bukkit.getServer());
                if (tpsValues instanceof double[]) {
                    return (double[]) tpsValues;
                }
            }

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
            // Avoid console spam on modern servers where recentTps no longer exists.
            if (!warnedRecentTpsUnavailable) {
                warnedRecentTpsUnavailable = true;
                Bukkit.getLogger().log(
                        Level.WARNING,
                        "[NeoPerformance] Unable to access server TPS via reflection; falling back to default TPS value.",
                        e
                );
            }
            return DEFAULT_TPS;
        }

    }

}