package com.neomechanical.neoperformance.performance.smart.smartReport.utils;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.integrations.spark.SparkRetrievers;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.performance.smart.smartReport.Grade;
import com.neomechanical.neoperformance.performance.smart.smartReport.grading.GradingSubjectsManager;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.IGradingSubject;

import java.util.List;

public class Grading {
    private Grading() {
    }

    /**
     * @param gradeValue between 1-100, 100 being an A+ and a 1 being an F
     *                   90-100 = A+
     *                   85-89 = A
     *                   80-84 = A-
     *                   75-79 = B+
     *                   70-74 = B
     *                   65-69 = C+
     *                   60-64 = C
     *                   55-59 = D+
     *                   50-54 = D
     *                   40-49 = E
     *                   0-39 = F
     * @return convert value to grade
     */
    public static Grade getGrade(int gradeValue) {
        Grade grade;
        if (gradeValue >= 90) {
            grade = Grade.APlus;
        } else if (gradeValue >= 85) {
            grade = Grade.A;
        } else if (gradeValue >= 80) {
            grade = Grade.ANeg;
        } else if (gradeValue >= 75) {
            grade = Grade.BPlus;
        } else if (gradeValue >= 70) {
            grade = Grade.B;
        } else if (gradeValue >= 65) {
            grade = Grade.CPlus;
        } else if (gradeValue >= 60) {
            grade = Grade.C;
        } else if (gradeValue >= 55) {
            grade = Grade.DPlus;
        } else if (gradeValue >= 50) {
            grade = Grade.D;
        } else if (gradeValue >= 40) {
            grade = Grade.E;
        } else if (gradeValue >= 0) {
            grade = Grade.F;
        } else {
            grade = Grade.F;
        }
        return grade;
    }

    public static String getFancyGrade(Grade grade) {
        switch (grade) {
            case APlus:
                return "<dark_green><bold>" + grade;
            case A:
                return "<color:#1bff17><bold>" + grade;
            case ANeg:
                return "<color:#62ff00><bold>" + grade;
            case BPlus:
                return "<color:#91ff00><bold>" + grade;
            case B:
                return "<color:#aeff00><bold>" + grade;
            case CPlus:
                return "<color:#ccff00><bold>" + grade;
            case C:
                return "<color:#e5ff00><bold>" + grade;
            case DPlus:
                return "<color:#f6ff00><bold>" + grade;
            case D:
                return "<color:#fff200><bold>" + grade;
            case E:
                return "<color:#ffc400><bold>" + grade;
            case F:
                return "<red>" + grade;
            default:
                return "grade";
        }
    }

    public static String getFancyGrade(int gradeValue) {
        Grade grade = getGrade(gradeValue);
        return getFancyGrade(grade);
    }

    public static Grade getServerGrade(NeoPerformance plugin, DataManager dataManager) {
        GradingSubjectsManager gradingSubjectsManager = new GradingSubjectsManager(plugin, (SparkRetrievers) dataManager.getHookIntegrations().getIntegration("spark"));
        List<IGradingSubject> gradingSubjects = gradingSubjectsManager.getAllGrades();
        //Calculate Overall Grade
        int gradeValues = 0;
        //Calculate overall grading
        for (IGradingSubject gradingSubject : gradingSubjects) {
            gradeValues += gradingSubject.performGrading().getGradeValue();
        }
        //Return the average
        return Grading.getGrade(gradeValues / gradingSubjects.size());
    }
}
