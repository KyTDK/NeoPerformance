package com.neomechanical.neoperformance.performance.modules.insight;

import org.bukkit.command.CommandSender;

public class Insights {
    public void openInsights(CommandSender commandSender) {
        InsightManager insightManager = new InsightManager();
        InsightGUIReport insightGUIReport = new InsightGUIReport();
        insightGUIReport.buildReport(insightManager.getInsightsMap())
                .openReport(commandSender);
    }
}
