package com.neomechanical.neoperformance.version.restore;

import com.neomechanical.neoperformance.performanceOptimiser.halt.CachedData;
import com.neomechanical.neoperformance.performanceOptimiser.managers.DataManager;
import com.neomechanical.neoutils.version.VersionWrapper;

public interface HeartBeatWrapper extends VersionWrapper {
    void restoreServer(CachedData cachedData, DataManager dataManager);
}
