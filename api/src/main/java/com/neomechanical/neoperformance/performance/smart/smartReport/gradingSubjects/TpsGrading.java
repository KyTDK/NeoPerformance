package com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.smart.smartReport.grading.GradeData;
import com.neomechanical.neoperformance.performance.utils.TpsUtils;

public class TpsGrading implements IGradingSubject {
    private final NeoPerformance plugin;

    public TpsGrading(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    @Override
    public GradeData performGrading() {
        int tps = (int) TpsUtils.getTPS(plugin);
        if (tps >= 18) {
            return new GradeData("Tps", tps * 5);
        } else if (tps >= 15) {
            return new GradeData("Tps", tps * 4);
        } else if (tps >= 10) {
            return new GradeData("Tps", tps * 3);
        } else if (tps >= 0) {
            return new GradeData("Tps", tps * 2);
        } else {
            return new GradeData("Tps", tps);
        }
    }
}
