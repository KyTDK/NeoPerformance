package com.neomechanical.neoperformance.performance.smart.smartReport.utils.spark;

import com.neomechanical.neoperformance.NeoPerformance;

public class SparkUtils {
    public static double MSTP() {
        return SparkRetrievers.MSTP();
    }

    public static boolean isInstalled(NeoPerformance plugin) {
        return plugin.getDataManager().getSparkData().isInstalled();
    }
}
