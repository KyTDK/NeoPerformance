package com.neomechanical.neoperformance.performance.managers.data;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
public class HaltActionData {
    //Halt actions
    private @NotNull List<String> haltActions;
}
