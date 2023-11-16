package com.neomechanical.neoperformance.performance.insight.elements.server.properties;

import com.neomechanical.neoperformance.performance.insight.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.ServerConfiguration;

public class Snooper extends InsightElement {
    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        return Boolean.parseBoolean(ServerConfiguration.getServerProperty(ServerConfiguration.ServerProperty.SNOOPER));
    }

    @Override
    public String recommendedValue() {
        return "false";
    }

    @Override
    public String currentValue() {
        return ServerConfiguration.getServerProperty(ServerConfiguration.ServerProperty.SNOOPER);
    }

    @Override
    public void fix() {
        ServerConfiguration.setServerProperty(ServerConfiguration.ServerProperty.SNOOPER, "false");
    }
}
