package com.neomechanical.neoperformance.managers;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.haltActions.HaltActionPojo;
import lombok.Getter;

@Getter
public class DataHandler {
    HaltActionPojo haltActionPojo = new HaltActionPojo();

    public DataHandler(NeoPerformance plugin) {
    }

}
