package com.neomechanical.neoperformance.performance.smart.smartReport;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.smart.smartReport.grading.GradingSubjectsManager;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.IGradingSubject;
import com.neomechanical.neoperformance.performance.smart.smartReport.report.PerformanceReport;

import java.util.List;

public class SmartReport {
    private final NeoPerformance plugin;

    public SmartReport(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    public PerformanceReport getPerformanceReport() {
        //Generate data
        GradingSubjectsManager gradingSubjectsManager = new GradingSubjectsManager(plugin);
        List<IGradingSubject> gradingSubjects = gradingSubjectsManager.getAllGrades();
        return new PerformanceReport.PerformanceReportBuilder()
                .addGrades(gradingSubjects)
                .build();
    }
}
