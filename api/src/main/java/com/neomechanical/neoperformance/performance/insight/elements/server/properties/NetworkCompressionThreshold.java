package com.neomechanical.neoperformance.performance.insight.elements.server.properties;

import com.neomechanical.neoperformance.performance.insight.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.ServerConfiguration;

public class NetworkCompressionThreshold extends InsightElement<Integer> {
    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        return Integer.parseInt(ServerConfiguration.getServerProperty(ServerConfiguration.ServerProperty.PACKET_COMPRESSION_LIMIT)) < recommendedValue;
    }

    @Override
    public void setDefaultValue() {
        recommendedValue = 512;
    }

    @Override
    public Integer currentValue() {
        return Integer.valueOf(ServerConfiguration.getServerProperty(ServerConfiguration.ServerProperty.PACKET_COMPRESSION_LIMIT));
    }

    @Override
    public void fix() {
        ServerConfiguration.setServerProperty(ServerConfiguration.ServerProperty.PACKET_COMPRESSION_LIMIT, String.valueOf(recommendedValue));
    }
}
