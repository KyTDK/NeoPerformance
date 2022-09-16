package com.neomechanical.neoperformance.performance.smart.smartReport.utils;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.SparkProvider;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.misc.DoubleAverageInfo;
import me.lucko.spark.api.statistic.types.GenericStatistic;
import org.bukkit.Bukkit;

public class SparkUtils {
    public static double MSTP() {
        Spark spark = SparkProvider.get();
        // Get the MSPT statistic (will be null on platforms that don't support measurement!)
        GenericStatistic<DoubleAverageInfo, StatisticWindow.MillisPerTick> mspt = spark.mspt();
        if (mspt == null) {
            NeoUtils.getInstance().getFancyLogger().warn("This platform does not support MSTP measurement");
            return 0;
        }
        // Retrieve the averages in the last minute
        DoubleAverageInfo msptLastMin = mspt.poll(StatisticWindow.MillisPerTick.MINUTES_1);
        return msptLastMin.mean();
    }

    public static void runIfEnabled(Runnable runnable) {
        if (Bukkit.getServer().getPluginManager().getPlugin("spark") != null) {
            runnable.run();
        }
    }
}
