package com.neomechanical.neoperformance.performanceOptimiser.managers;

public class TweakDataManager {
    private static TweakDataManager instance;
    private TweakData tweakData;

    public TweakDataManager(TweakData tweakData) {
        this.tweakData = tweakData;
    }

    public TweakData getTweakData() {
        return this.tweakData;
    }
    public void setTweakData(TweakData tweakData) {
        this.tweakData = tweakData;
    }
}
