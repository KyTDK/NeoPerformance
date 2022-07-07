package com.neomechanical.neoperformance.performanceOptimiser.managers;

public class TweakDataManager {
    private boolean manualHalt=false;
    private TweakData tweakData;

    public TweakDataManager() {
        this.tweakData = new performanceTweaksConfiguration().loadTweakSettings();
    }

    public TweakData getTweakData() {
        return this.tweakData;
    }
    public void setTweakData(TweakData tweakData) {
        this.tweakData = tweakData;
    }
    public boolean isManualHalt() {
        return manualHalt;
    }
    public void toogleManualHalt() {
        //toggle boolean manualHalt
        manualHalt = !manualHalt;
    }
}
