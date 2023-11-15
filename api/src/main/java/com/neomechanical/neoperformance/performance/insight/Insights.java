package com.neomechanical.neoperformance.performance.insight;

import org.bukkit.command.CommandSender;

public class Insights {
    public void sendInsights(CommandSender commandSender) {
        InsightManager insightManager = new InsightManager();
        InsightReport insightReport = new InsightReport();
        insightReport.buildReport(insightManager.getInsightsMap())
                .sendReport(commandSender);
    }
}
