package com.neomechanical.neoperformance.performance.smart.smartReport.grading;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.CPUUsageGrading;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.IGradingSubject;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.PlayersToPerformanceGrading;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.TpsGrading;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.dependent.SparkMSTPGrading;
import com.neomechanical.neoperformance.performance.smart.smartReport.utils.SparkUtils;

import java.util.ArrayList;
import java.util.List;

public class GradingSubjectsManager {
    List<IGradingSubject> gradingSubjects = new ArrayList<>();

    public GradingSubjectsManager(NeoPerformance plugin) {
        gradingSubjects.add(new TpsGrading(plugin));
        gradingSubjects.add(new PlayersToPerformanceGrading());
        gradingSubjects.add(new CPUUsageGrading());
        SparkUtils.runIfEnabled(() -> gradingSubjects.add(new SparkMSTPGrading()));
    }

    public List<IGradingSubject> getAllGrades() {
        return gradingSubjects;
    }
}
