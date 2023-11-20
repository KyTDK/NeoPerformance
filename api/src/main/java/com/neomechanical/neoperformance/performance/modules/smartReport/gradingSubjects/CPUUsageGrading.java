package com.neomechanical.neoperformance.performance.modules.smartReport.gradingSubjects;

import com.neomechanical.neoperformance.performance.modules.smartReport.grading.GradeData;
import com.neomechanical.neoperformance.performance.modules.smartReport.utils.CPU;

public class CPUUsageGrading implements IGradingSubject {
    @Override
    public GradeData performGrading() {
        double load;
        int value;
        try {
            load = CPU.getProcessCpuLoad() * 100;
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
