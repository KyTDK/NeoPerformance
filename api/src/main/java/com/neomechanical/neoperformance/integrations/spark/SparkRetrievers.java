package com.neomechanical.neoperformance.integrations.spark;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import me.lucko.spark.api.Spark;

public class SparkRetrievers {
    public double MSPT() {
        Spark spark = me.lucko.spark.api.SparkProvider.get();
        // Get the MSPT statistic (will be null on platforms that don't support measurement!)
        me.lucko.spark.api.statistic.types.GenericStatistic<me.lucko.spark.api.statistic.misc.DoubleAverageInfo, me.lucko.spark.api.statistic.StatisticWindow.MillisPerTick> mspt = spark.mspt();
        if (mspt == null) {
            NeoUtils.getNeoUtilities().getFancyLogger().warn("This platform does not support MSPT measurement");
            return 0;
        }
        // Retrieve the averages in the last minute
        me.lucko.spark.api.statistic.misc.DoubleAverageInfo msptLastMin = mspt.poll(me.lucko.spark.api.statistic.StatisticWindow.MillisPerTick.MINUTES_1);
        return msptLastMin.mean();
    }
}
