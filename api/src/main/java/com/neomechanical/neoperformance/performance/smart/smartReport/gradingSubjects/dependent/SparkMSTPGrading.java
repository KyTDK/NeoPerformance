package com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.dependent;

import com.neomechanical.neoperformance.performance.smart.smartReport.grading.GradeData;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.IGradingSubject;
import com.neomechanical.neoperformance.performance.smart.smartReport.utils.SparkUtils;

public class SparkMSTPGrading implements IGradingSubject {
    @Override
    public GradeData performGrading() {
        return new GradeData("MSTP minute average", (int) ((5 / SparkUtils.MSTP()) * 100));
    }
}