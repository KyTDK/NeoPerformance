package com.neomechanical.neoperformance.performance.haltActions;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.modules.smartClear.SmartClear;

public class RegisterHaltActions {
    private final NeoPerformance plugin;
    private final HaltActions haltActions;

    public RegisterHaltActions(NeoPerformance plugin) {
        this.plugin = plugin;
        haltActions = new HaltActions(plugin);
    }

    public void registerActions() {
        haltActions.registerHaltAction("smartclear",
                (tps) -> SmartClear.scanThenExterminate());
    }
}
