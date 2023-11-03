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
    F("F");
    private final String grade;

    Grade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return grade;
    }
}
