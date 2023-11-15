package com.neomechanical.neoperformance.performance.insight.elements;

import com.neomechanical.neoperformance.performance.insight.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.ServerProperties;
import org.bukkit.Bukkit;

public class RenderDistance extends InsightElement {
    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        return Bukkit.getViewDistance() > 4;
    }

    @Override
    public String recommendedValue() {
        return "4";
    }

    @Override
    public String currentValue() {
        return ServerProperties.getServerProperty(ServerProperties.ServerProperty.VIEW_DISTANCE);
    }

    @Override
    public void fix() {
        ServerProperties.setServerProperty(ServerProperties.ServerProperty.VIEW_DISTANCE, "4");
    }
}
