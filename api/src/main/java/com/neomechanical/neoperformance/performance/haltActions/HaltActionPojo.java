package com.neomechanical.neoperformance.performance.haltActions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class HaltActionPojo {
    private final Map<String, Consumer<Double>> haltActionMap = new HashMap<>();

    public void addAction(String name, Consumer<Double> actionConsumer) {
        haltActionMap.put(name, actionConsumer);
    }

    public Map<String, Consumer<Double>> getHaltActionMap() {
        return haltActionMap;
    }
}
