package com.neomechanical.neoperformance.utils;

import org.bukkit.Bukkit;

public class Logger {
    static String prefix = "[NeoPerformance] ";
    public static void warn(String message) {
        Bukkit.getLogger().warning(prefix + message);
    }

    public static void info(String message) {
        Bukkit.getLogger().info(prefix + message);
    }

    public static void severe(String message) {
        Bukkit.getLogger().severe(prefix + message);
    }

    public static void outdated() {
        Bukkit.getLogger().warning("[NeoPerformance] WARNING, PLUGIN IS EITHER OUTDATED OR NOT SUPPORTED ON THIS VERSION OF MINECRAFT");
    }
}
