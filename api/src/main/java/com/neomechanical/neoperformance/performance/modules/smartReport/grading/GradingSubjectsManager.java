package com.neomechanical.neoperformance.performance.modules.smartReport.grading;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.integrations.spark.SparkRetrievers;
import com.neomechanical.neoperformance.performance.modules.smartReport.gradingSubjects.CPUUsageGrading;
import com.neomechanical.neoperformance.performance.modules.smartReport.gradingSubjects.IGradingSubject;
import com.neomechanical.neoperformance.performance.modules.smartReport.gradingSubjects.PlayersToPerformanceGrading;
import com.neomechanical.neoperformance.performance.modules.smartReport.gradingSubjects.TpsGrading;
import com.neomechanical.neoperformance.performance.modules.smartReport.gradingSubjects.dependent.SparkMSPTGrading;

import java.util.ArrayList;
import java.util.List;

public class GradingSubjectsManager {
    List<IGradingSubject> gradingSubjects = new ArrayList<>();

    public GradingSubjectsManager(NeoPerformance plugin, SparkRetrievers sparkRetrievers) {
        gradingSubjects.add(new TpsGrading(plugin));
        gradingSubjects.add(new PlayersToPerformanceGrading());
        gradingSubjects.add(new CPUUsageGrading());
        if (sparkRetrievers != null) {
            if (sparkRetrievers.MSPTSupported()) {
                gradingSubjects.add(new SparkMSPTGrading(sparkRetrievers));
            }
        }
    }

    public List<IGradingSubject> getAllGrades() {
        return gradingSubjects;
    }
}
