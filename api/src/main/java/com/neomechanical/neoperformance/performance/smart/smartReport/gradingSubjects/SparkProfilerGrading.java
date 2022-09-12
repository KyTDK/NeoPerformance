package com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects;

import com.neomechanical.neoperformance.performance.smart.smartReport.grading.GradeData;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.SparkProvider;

public class SparkProfilerGrading implements IGradingSubject {
    @Override
    public GradeData performGrading() {
        Spark spark = SparkProvider.get();
        spark.mspt().
        return null;
    }
}
