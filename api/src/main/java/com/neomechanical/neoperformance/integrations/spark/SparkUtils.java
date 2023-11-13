package com.neomechanical.neoperformance.integrations.spark;

import com.neomechanical.neoperformance.NeoPerformance;

public class SparkUtils {
    public static double MSPT() {
        return SparkRetrievers.MSPT();
    }

    public static double CPUSystem() {
        return SparkRetrievers.CPUSystem();
    }

    public static double CPUProcess() {
        return SparkRetrievers.CPUProcess();
    }

    public static boolean isInstalled(NeoPerformance plugin) {
        return plugin.getDataManager().getSparkData().isInstalled();
    }
}
