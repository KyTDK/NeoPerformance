package com.neomechanical.neoperformance.versions.legacy.HeartBeat;

public interface HeartBeatWrapper {
    double getUpdatedTPS();

    void start();

    void restoreServer();
}
