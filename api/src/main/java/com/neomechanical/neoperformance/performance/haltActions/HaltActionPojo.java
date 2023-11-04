package com.neomechanical.neoperformance.performance.haltActions;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Getter
public class HaltActionPojo {
    private final Map<String, Consumer<Double>> haltActionMap = new HashMap<>();

    public void addAction(String name, Consumer<Double> actionConsumer) {
        haltActionMap.put(name, actionConsumer);
    }

}
