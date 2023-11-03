package com.neomechanical.neoperformance.performance.managers.data;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class LagNotifierData {
    private @NotNull
    Integer runInterval;
    private @NotNull
    Integer entitiesInChunkNotify;
    private @NotNull
    Integer clusterSizeNotify;
}
