package com.neomechanical.neoperformance.performanceOptimiser.managers.data;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class TweakData {
    //Performance tweak settings
    private @NotNull
    final Integer tpsHaltAt;//18 is the default value.
    private @NotNull
    Boolean notifyAdmin;//true is the default value.
    private @NotNull
    Boolean broadcastHalt;
    private @NotNull
    Integer mobCap;
    private @NotNull
    Integer mobCapRadius;
    private @NotNull
    Integer explosionCap;
    private @NotNull
    Integer heartBeatRate;
}
