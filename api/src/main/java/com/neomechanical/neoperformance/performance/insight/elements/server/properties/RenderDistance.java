package com.neomechanical.neoperformance.performance.insight.elements.server.properties;

import com.neomechanical.neoperformance.performance.insight.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.ServerConfiguration;
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
        return ServerConfiguration.getServerProperty(ServerConfiguration.ServerProperty.VIEW_DISTANCE);
    }

    @Override
    public void fix() {
        ServerConfiguration.setServerProperty(ServerConfiguration.ServerProperty.VIEW_DISTANCE, "4");
    }
}
