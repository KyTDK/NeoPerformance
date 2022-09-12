package com.neomechanical.neoperformance.performance.smart.smartReport.utils;

import com.neomechanical.neoconfig.neoutils.server.resources.DataSize;

public class Memory {
    public static long usedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return DataSize.ofBytes(runtime.totalMemory() - runtime.freeMemory()).toMegabytes();
    }
    public static long freeMemory() {
        Runtime runtime = Runtime.getRuntime();
        return DataSize.ofBytes(runtime.freeMemory()).toMegabytes();
    }
    public static long maxMemory() {
        Runtime runtime = Runtime.getRuntime();
        return DataSize.ofBytes(runtime.maxMemory()).toGigabytes();
    }
}
