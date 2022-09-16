package com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.smart.smartReport.grading.GradeData;
import com.neomechanical.neoperformance.performance.smart.smartReport.utils.CPU;

public class CPUUsageGrading implements IGradingSubject {
    private final NeoPerformance plugin;

    public CPUUsageGrading(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    @Override
    public GradeData performGrading() {
        double load;
        int value;
        try {
            load = CPU.getProcessCpuLoad(plugin) * 100;
            if (load <= 20) {
                value = 100;
            } else {
                value = (int) (100 - load);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new GradeData("CPU usage", value);
    }
}
