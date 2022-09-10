package com.neomechanical.neoperformance.performance.smart.smartReport;

public enum Grade {
    APlus("A+"),
    A("A"),
    ANeg("A-"),
    BPlus("B+"),
    B("B"),
    CPlus("C+"),
    C("C"),
    DPlus("D+"),
    D("D"),
    E("E"),
    F("F"),
    UNKNOWN("UNKNOWN");
    private final String grade;

    Grade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return grade;
    }
}
