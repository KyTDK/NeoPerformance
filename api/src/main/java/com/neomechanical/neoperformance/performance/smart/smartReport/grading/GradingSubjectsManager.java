package com.neomechanical.neoperformance.performance.smart.smartReport.grading;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.CPUUsageGrading;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.IGradingSubject;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.PlayersToPerformanceGrading;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.TpsGrading;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.dependent.SparkMSTPGrading;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class GradingSubjectsManager {
    List<IGradingSubject> gradingSubjects = new ArrayList<>();

    public GradingSubjectsManager(NeoPerformance plugin) {
        gradingSubjects.add(new TpsGrading(plugin));
        gradingSubjects.add(new PlayersToPerformanceGrading());
        gradingSubjects.add(new CPUUsageGrading());
        if (Bukkit.getServer().getPluginManager().getPlugin("spark") != null) {
            gradingSubjects.add(new SparkMSTPGrading());
        }
    }

    public List<IGradingSubject> getAllGrades() {
        return gradingSubjects;
    }
}
