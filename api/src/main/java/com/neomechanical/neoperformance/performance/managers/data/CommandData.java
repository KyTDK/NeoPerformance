package com.neomechanical.neoperformance.performance.managers.data;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class CommandData {
    //Smart Clear
    private @NotNull Integer defaultClusterSize;
    private @NotNull String[] smartClearExcludeEntities;
}
