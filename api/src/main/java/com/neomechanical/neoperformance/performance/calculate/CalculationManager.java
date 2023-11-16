package com.neomechanical.neoperformance.performance.calculate;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

public class CalculationManager {

    private static double cpuScore = 1; //Default score
    private static double ramScore = 1; //Default score
    private static final double averageRam = 8;
    /*
        The System Score is used to evaluate certain
     */
    public static void init() {
        calculateSystemScores();
    }

    private static void calculateSystemScores() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();

        ramScore = (double) getSystemRAM(hardware) / (1024 * 1024 * 1024) / averageRam;
        cpuScore = calculateCPUScore(hardware);
    }

    private static double calculateCPUScore(HardwareAbstractionLayer hardware) {
        CentralProcessor processor = hardware.getProcessor();

        int availableProcessors = processor.getLogicalProcessorCount();
        double maxClockSpeed = getMaxClockSpeed(processor);
        double baseScore = 1.0;

        // Maybe need to adjusted scaling factors
        double coreScalingFactor = 0.3;
        double clockSpeedScalingFactor = 0.2;

        double scaledScore = baseScore + (availableProcessors * coreScalingFactor) + (maxClockSpeed * clockSpeedScalingFactor);
        return Math.max(0.0, scaledScore);
    }

    private static double getMaxClockSpeed(CentralProcessor processor) {
        return processor.getMaxFreq() / 1e9;
    }

    private static long getSystemRAM(HardwareAbstractionLayer hardware) {
        GlobalMemory memory = hardware.getMemory();
        return memory.getTotal();
    }

    public static double getCpuScore() {
        return cpuScore;
    }

    public static double getRamScore() {
        return ramScore;
    }
}
