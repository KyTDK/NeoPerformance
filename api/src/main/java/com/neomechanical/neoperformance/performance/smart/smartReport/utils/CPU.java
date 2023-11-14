package com.neomechanical.neoperformance.performance.smart.smartReport.utils;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class CPU {
    private static double processLoad;
    private static double systemLoad;

    public static double getProcessCpuLoad() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
        double load;
        load = osBean.getProcessCpuLoad();
        if (load > 0) {
            processLoad = load;
        }
        return processLoad;
    }

    public static double getSystemCpuLoad() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
        double load;
        load = osBean.getSystemCpuLoad();
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
