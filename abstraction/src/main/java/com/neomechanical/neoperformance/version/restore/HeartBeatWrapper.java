package com.neomechanical.neoperformance.version.restore;

import com.neomechanical.neoutils.version.VersionWrapper;

public interface HeartBeatWrapper extends VersionWrapper {
    void restoreServer(CachedData cachedData, DataManager dataManager);
}
