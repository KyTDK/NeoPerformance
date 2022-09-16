package com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.dependent;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import com.neomechanical.neoperformance.performance.smart.smartReport.grading.GradeData;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.IGradingSubject;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.SparkProvider;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.misc.DoubleAverageInfo;
import me.lucko.spark.api.statistic.types.GenericStatistic;

public class SparkMSTPGrading implements IGradingSubject {
    @Override
    public GradeData performGrading() {
        Spark spark = SparkProvider.get();
        // Get the MSPT statistic (will be null on platforms that don't support measurement!)
        GenericStatistic<DoubleAverageInfo, StatisticWindow.MillisPerTick> mspt = spark.mspt();
        if (mspt == null) {
            NeoUtils.getInstance().getFancyLogger().warn("This platform does not support MSTP measurement");
            return null;
        }
        // Retrieve the averages in the last minute
        DoubleAverageInfo msptLastMin = mspt.poll(StatisticWindow.MillisPerTick.MINUTES_1);
        double msptMean = msptLastMin.mean();
        return new GradeData("MSTP minute average", (int) ((5 / msptMean) * 100));
    }
}
