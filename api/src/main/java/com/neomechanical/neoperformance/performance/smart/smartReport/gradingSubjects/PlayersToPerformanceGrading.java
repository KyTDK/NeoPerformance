package com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects;

import com.neomechanical.neoperformance.performance.smart.smartReport.grading.GradeData;
import org.bukkit.Bukkit;

public class PlayersToPerformanceGrading implements IGradingSubject {
    @Override
    public GradeData performGrading() {
        int playerCount = Bukkit.getOnlinePlayers().size();
        long freeMemory = Runtime.getRuntime().freeMemory() / 1048576; //in MB
        //Memory to playerCount ratio
        long ratio = freeMemory / playerCount;
        int grade;
        if (ratio >= 75) {
            grade = 100;
        } else {
            grade = (int) ratio;
        }
        return new GradeData("Players to server resources", grade);
    }
}
