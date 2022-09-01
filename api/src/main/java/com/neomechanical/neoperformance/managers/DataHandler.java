package com.neomechanical.neoperformance.managers;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.haltActions.HaltActionPojo;

public class DataHandler {
    private final NeoPerformance plugin;
    HaltActionPojo haltActionPojo = new HaltActionPojo();

    public DataHandler(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    public HaltActionPojo getHaltActionPojo() {
        return haltActionPojo;
    }
}
