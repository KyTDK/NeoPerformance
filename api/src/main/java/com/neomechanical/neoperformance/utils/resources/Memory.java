package com.neomechanical.neoperformance.utils.resources;

public class Memory {
    public static long getMemory() {
        Runtime r = Runtime.getRuntime();
        return (r.totalMemory() - r.freeMemory()) / 1048576;
    }
}
