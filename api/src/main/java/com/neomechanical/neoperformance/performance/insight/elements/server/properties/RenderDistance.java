package com.neomechanical.neoperformance.performance.insight.elements.server.properties;

import com.neomechanical.neoperformance.performance.insight.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.ServerConfiguration;

public class RenderDistance extends InsightElement<Integer> {
    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        return Integer.parseInt(ServerConfiguration.getServerProperty(ServerConfiguration.ServerProperty.VIEW_DISTANCE)) > recommendedValue;
    }

    @Override
    public void setDefaultValue() {
        recommendedValue = 4;
    }

    @Override
    public Integer currentValue() {
        return Integer.valueOf(ServerConfiguration.getServerProperty(ServerConfiguration.ServerProperty.VIEW_DISTANCE));
    }

    @Override
    public void fix() {
        ServerConfiguration.setServerProperty(ServerConfiguration.ServerProperty.VIEW_DISTANCE, String.valueOf(recommendedValue));
    }
}
