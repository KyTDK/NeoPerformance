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
}
