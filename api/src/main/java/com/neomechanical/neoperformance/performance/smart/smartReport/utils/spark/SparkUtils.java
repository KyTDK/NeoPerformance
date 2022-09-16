package com.neomechanical.neoperformance.performance.smart.smartReport.utils.spark;

import com.neomechanical.neoperformance.NeoPerformance;

public class SparkUtils {
    public static double MSTP() {
        return SparkRetrievers.MSTP();
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
