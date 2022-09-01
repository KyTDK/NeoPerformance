package com.neomechanical.neoperformance.performance.haltActions;

import com.neomechanical.neoperformance.NeoPerformance;

import java.util.Map;
import java.util.function.Consumer;

public class HaltActions {
    private final NeoPerformance plugin;
    private final HaltActionPojo haltActionPojo;

    public HaltActions(NeoPerformance plugin) {
        this.plugin = plugin;
        this.haltActionPojo = plugin.getDataHandler().getHaltActionPojo();
    }

    public HaltActions registerHaltAction(String name, Consumer<Double> actionConsumer) {
        haltActionPojo.addAction(name, actionConsumer);
        return this;
    }

    public Map<String, Consumer<Double>> getActionMap() {
        return haltActionPojo.getHaltActionMap();
    }
}
