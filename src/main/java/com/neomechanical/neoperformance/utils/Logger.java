package com.neomechanical.neoperformance.utils;

import org.bukkit.Bukkit;

public class Logger {
    public static void warn(String message) {
        Bukkit.getLogger().warning("[NeoPerformance] " + message);
    }

    public static void info(String message) {
        Bukkit.getLogger().info("[NeoPerformance] " + message);
    }

    public static void severe(String message) {
        Bukkit.getLogger().severe("[NeoPerformance] " + message);
    }

    public static void outdated() {
        Bukkit.getLogger().warning("[NeoPerformance] WARNING, PLUGIN IS EITHER OUTDATED OR NOT SUPPORTED ON THIS VERSION OF MINECRAFT");
    }
}
