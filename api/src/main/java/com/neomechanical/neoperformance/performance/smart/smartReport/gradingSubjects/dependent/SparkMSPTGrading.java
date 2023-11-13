package com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.dependent;

import com.neomechanical.neoperformance.integrations.spark.SparkUtils;
import com.neomechanical.neoperformance.performance.smart.smartReport.grading.GradeData;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.IGradingSubject;

public class SparkMSPTGrading implements IGradingSubject {
    @Override
    public GradeData performGrading() {
        return new GradeData("MSPT minute average", (int) ((50 / SparkUtils.MSPT()) * 100));
    }
}
