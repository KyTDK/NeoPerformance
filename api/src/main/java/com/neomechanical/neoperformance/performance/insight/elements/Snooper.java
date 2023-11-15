package com.neomechanical.neoperformance.performance.insight.elements;

import com.neomechanical.neoperformance.performance.insight.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.ServerProperties;

public class Snooper extends InsightElement {
    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        return Boolean.parseBoolean(ServerProperties.getServerProperty(ServerProperties.ServerProperty.SNOOPER));
    }

    @Override
    public String recommendedValue() {
        return "false";
    }

    @Override
    public String currentValue() {
        return ServerProperties.getServerProperty(ServerProperties.ServerProperty.SNOOPER);
    }

    @Override
    public void fix() {
        ServerProperties.setServerProperty(ServerProperties.ServerProperty.SNOOPER, "false");
    }
}
