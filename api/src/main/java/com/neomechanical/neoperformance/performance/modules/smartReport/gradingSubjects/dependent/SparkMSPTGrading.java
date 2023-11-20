package com.neomechanical.neoperformance.performance.modules.smartReport.gradingSubjects.dependent;

import com.neomechanical.neoperformance.integrations.spark.SparkRetrievers;
import com.neomechanical.neoperformance.performance.modules.smartReport.grading.GradeData;
import com.neomechanical.neoperformance.performance.modules.smartReport.gradingSubjects.IGradingSubject;

public class SparkMSPTGrading implements IGradingSubject {
    private final SparkRetrievers sparkRetrievers;

    public SparkMSPTGrading(SparkRetrievers sparkRetrievers) {
        this.sparkRetrievers = sparkRetrievers;
    }
    @Override
    public GradeData performGrading() {
        return new GradeData("MSPT minute average", (int) ((50 / sparkRetrievers.MSPT()) * 100));
    }
}
