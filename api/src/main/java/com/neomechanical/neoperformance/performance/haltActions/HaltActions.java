package com.neomechanical.neoperformance.performance.haltActions;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.managers.DataHandler;
import com.neomechanical.neoperformance.performance.managers.DataManager;

import java.util.Map;
import java.util.function.Consumer;

public class HaltActions {
    private static NeoPerformance plugin;
    private final HaltActionPojo haltActionPojo;
    private static DataManager dataManager;
    private static DataHandler dataHandler;

    public HaltActions(NeoPerformance plugin) {
        HaltActions.plugin = plugin;
        this.haltActionPojo = plugin.getDataHandler().getHaltActionPojo();
        dataManager = plugin.getDataManager();
        dataHandler = plugin.getDataHandler();
    }

    public HaltActions registerHaltAction(String name, Consumer<Double> actionConsumer) {
        haltActionPojo.addAction(name, actionConsumer);
        return this;
    }

    public Map<String, Consumer<Double>> getActionMap() {
        return haltActionPojo.getHaltActionMap();
    }

    public static void runHaltActions(double tps) {
        Map<String, Consumer<Double>> actionMap = dataHandler.getHaltActionPojo().getHaltActionMap();
        for (String actionName : dataManager.getHaltActionData().getHaltActions()) {
            if (actionMap.containsKey(actionName.toLowerCase())) {
                actionMap.get(actionName.toLowerCase()).accept(tps);
            } else if (!actionName.equals("null")) {
                plugin.getFancyLogger().warn(actionName + " is not a halt action");
            }
        }
    }
}
