package com.neomechanical.neoperformance.performance.smart.smartReport.utils;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.integrations.spark.SparkUtils;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class CPU {
    private static double processLoad;
    private static double systemLoad;

    public static double getProcessCpuLoad(NeoPerformance plugin) {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
        double load;
        if (SparkUtils.isInstalled(plugin)) {
            load = SparkUtils.CPUProcess();
        } else {
            load = osBean.getProcessCpuLoad();
        }
        if (load > 0) {
            processLoad = load;
        }
        return processLoad;
    }

    public static double getSystemCpuLoad(NeoPerformance plugin) {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
        double load;
        if (SparkUtils.isInstalled(plugin)) {
            load = SparkUtils.CPUSystem();
        } else {
            load = osBean.getSystemCpuLoad();
        }
        if (load > 0) {
            systemLoad = load;
        }
        return systemLoad;
    }

    public static int availableProcessors() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.availableProcessors();
    }
}
