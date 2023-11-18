package com.neomechanical.neoperformance.performance.insight.elements.server.properties;

import com.neomechanical.neoperformance.performance.insight.elements.InsightElement;
import com.neomechanical.neoperformance.performance.insight.utils.ServerConfiguration;
import org.bukkit.entity.Player;

public class Snooper extends InsightElement<Boolean> {
    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        return Boolean.parseBoolean(ServerConfiguration.getServerProperty(ServerConfiguration.ServerProperty.SNOOPER));
    }

    @Override
    public void setDefaultValue() {
        recommendedValue = false;
    }

    @Override
    public Boolean currentValue() {
        return Boolean.valueOf(ServerConfiguration.getServerProperty(ServerConfiguration.ServerProperty.SNOOPER));
    }

    @Override
    protected void fixInternally(Player player) {
        ServerConfiguration.setServerProperty(ServerConfiguration.ServerProperty.SNOOPER, String.valueOf(recommendedValue));
    }
}
