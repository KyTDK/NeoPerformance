package com.neomechanical.neoperformance.performanceOptimiser.halt.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsMethods {
    public static <T> T mostCommon(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<T, Integer> max = null;

        for (Map.Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }
        if (max == null) {
            //return null;
            return null;
        }
        return max.getKey();
    }
    public static <T> int frequency(List<T> list, T t) {
        return Collections.frequency(list, t);
    }
}
