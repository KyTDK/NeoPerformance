package com.neomechanical.neoperformance.performance.smart.smartReport.utils;

import com.neomechanical.neoperformance.performance.smart.smartReport.Grade;

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
            grade = Grade.UNKNOWN;
        }
        return grade;
    }

    public static String getFancyGrade(Grade grade) {
        switch (grade) {
            case APlus:
                return "<dark_green>" + grade + "</color>";
            case A:
                return "<colour:#1bff17>" + grade + "</color>";
            case ANeg:
                return "<color:#62ff00>" + grade + "</color>";
            case BPlus:
                return "<color:#91ff00>" + grade + "</color>";
            case B:
                return "<color:#aeff00>" + grade + "</color>";
            case CPlus:
                return "<color:#ccff00>" + grade + "</color>";
            case C:
                return "<color:#e5ff00>" + grade + "</color>";
            case DPlus:
                return "<color:#f6ff00>" + grade + "</color>";
            case D:
                return "<color:#fff200>" + grade + "</color>";
            case E:
                return "<color:#ffc400>" + grade + "</color>";
            case F:
                return "<red>" + grade + "</color>";
            default:
                return "grade";
        }
    }

    public static String getFancyGrade(int gradeValue) {
        Grade grade = getGrade(gradeValue);
        return getFancyGrade(grade);
    }
}
