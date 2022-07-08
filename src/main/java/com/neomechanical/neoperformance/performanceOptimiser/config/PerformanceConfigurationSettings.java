package com.neomechanical.neoperformance.performanceOptimiser.config;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.managers.TweakData;

public interface PerformanceConfigurationSettings {
    default TweakData getTweakData() {
        return NeoPerformance.getTweakDataManager().getTweakData();
    }
}
