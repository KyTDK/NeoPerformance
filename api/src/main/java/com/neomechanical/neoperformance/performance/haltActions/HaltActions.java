package com.neomechanical.neoperformance.performance.haltActions;

import com.neomechanical.neoutils.NeoUtils;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.managers.DataHandler;
import com.neomechanical.neoperformance.performance.managers.DataManager;

import java.util.Map;
import java.util.function.Consumer;

public class HaltActions {
    private final HaltActionPojo haltActionPojo;

    public HaltActions(NeoPerformance plugin) {
        this.haltActionPojo = plugin.getPerformanceDataHandler().getHaltActionPojo();
    }

    public HaltActions registerHaltAction(String name, Consumer<Double> actionConsumer) {
        haltActionPojo.addAction(name, actionConsumer);
        return this;
    }

    public Map<String, Consumer<Double>> getActionMap() {
        return haltActionPojo.getHaltActionMap();
    }

    public static void runHaltActions(NeoPerformance plugin, double tps) {
        DataManager dataManager = plugin.getDataManager();
        DataHandler dataHandler = plugin.getPerformanceDataHandler();
        Map<String, Consumer<Double>> actionMap = dataHandler.getHaltActionPojo().getHaltActionMap();
        for (String actionName : dataManager.haltActionNames()) {
            if (actionMap.containsKey(actionName.toLowerCase())) {
                actionMap.get(actionName.toLowerCase()).accept(tps);
            } else if (!actionName.equals("null")) {
                NeoUtils.getNeoUtilities().getFancyLogger().warn(actionName + " is not a halt action");
            }
        }
    }
}
