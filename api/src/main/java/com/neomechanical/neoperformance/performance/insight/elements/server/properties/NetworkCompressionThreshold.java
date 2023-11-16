package com.neomechanical.neoperformance.performance.insight.elements.server.properties;

import com.neomechanical.neoperformance.performance.insight.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.ServerConfiguration;

public class NetworkCompressionThreshold extends InsightElement {
    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        return Integer.parseInt(ServerConfiguration.getServerProperty(ServerConfiguration.ServerProperty.PACKET_COMPRESSION_LIMIT)) < 512;
    }

    @Override
    public String recommendedValue() {
        return "512";
    }

    @Override
    public String currentValue() {
        return ServerConfiguration.getServerProperty(ServerConfiguration.ServerProperty.PACKET_COMPRESSION_LIMIT);
    }

    @Override
    public void fix() {
        ServerConfiguration.setServerProperty(ServerConfiguration.ServerProperty.PACKET_COMPRESSION_LIMIT, "512");
    }
}
