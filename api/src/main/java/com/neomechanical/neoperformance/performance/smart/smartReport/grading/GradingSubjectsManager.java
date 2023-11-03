package com.neomechanical.neoperformance.performance.smart.smartReport.grading;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.CPUUsageGrading;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.IGradingSubject;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.PlayersToPerformanceGrading;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.TpsGrading;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.dependent.SparkMSPTGrading;
import com.neomechanical.neoperformance.performance.smart.smartReport.utils.spark.SparkUtils;

import java.util.ArrayList;
import java.util.List;

public class GradingSubjectsManager {
    List<IGradingSubject> gradingSubjects = new ArrayList<>();

    public GradingSubjectsManager(NeoPerformance plugin) {
        gradingSubjects.add(new TpsGrading(plugin));
        gradingSubjects.add(new PlayersToPerformanceGrading());
        gradingSubjects.add(new CPUUsageGrading(plugin));
        if (SparkUtils.isInstalled(plugin)) {
            gradingSubjects.add(new SparkMSPTGrading());
        }
    }

    public List<IGradingSubject> getAllGrades() {
        return gradingSubjects;
    }
}
