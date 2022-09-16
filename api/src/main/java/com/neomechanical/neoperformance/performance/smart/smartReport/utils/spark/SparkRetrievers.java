package com.neomechanical.neoperformance.performance.smart.smartReport.utils.spark;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.types.DoubleStatistic;

public class SparkRetrievers {
    public static double MSTP() {
        Spark spark = me.lucko.spark.api.SparkProvider.get();
        // Get the MSPT statistic (will be null on platforms that don't support measurement!)
        me.lucko.spark.api.statistic.types.GenericStatistic<me.lucko.spark.api.statistic.misc.DoubleAverageInfo, me.lucko.spark.api.statistic.StatisticWindow.MillisPerTick> mspt = spark.mspt();
        if (mspt == null) {
            NeoUtils.getInstance().getFancyLogger().warn("This platform does not support MSPT measurement");
            return 0;
        }
        // Retrieve the averages in the last minute
        me.lucko.spark.api.statistic.misc.DoubleAverageInfo msptLastMin = mspt.poll(me.lucko.spark.api.statistic.StatisticWindow.MillisPerTick.MINUTES_1);
        return msptLastMin.mean();
    }

    public static double CPUSystem() {
        Spark spark = me.lucko.spark.api.SparkProvider.get();
        // Get the CPU Usage statistic
        DoubleStatistic<StatisticWindow.CpuUsage> cpuUsage = spark.cpuSystem();
        // Retrieve the average usage percent in the last minute
        return cpuUsage.poll(StatisticWindow.CpuUsage.MINUTES_1);
    }

    public static double CPUProcess() {
        Spark spark = me.lucko.spark.api.SparkProvider.get();
        // Get the CPU Usage statistic
        DoubleStatistic<StatisticWindow.CpuUsage> cpuUsage = spark.cpuProcess();
        // Retrieve the average usage percent in the last minute
        return cpuUsage.poll(StatisticWindow.CpuUsage.MINUTES_1);
    }
}
