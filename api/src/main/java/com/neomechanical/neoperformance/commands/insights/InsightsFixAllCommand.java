package com.neomechanical.neoperformance.commands.insights;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.insight.InsightManager;
import org.bukkit.command.CommandSender;

public class InsightsFixAllCommand extends Command {
    @Override
    public String getName() {
        return "all";
    }

    @Override
    public String getDescription() {
        return "Fix all";
    }

    @Override
    public String getPermission() {
        return "neoperformance.fix.all";
    }

    @Override
    public String getSyntax() {
        return "/np insights fix all";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        InsightManager insightManager = new InsightManager();
        if (insightManager.getInsightsMap().isEmpty()) {
            MessageUtil.sendMM(commandSender, NeoPerformance.getLanguageManager().getString("insights.noElements", null));
            return;
        }
        insightManager.getInsightsMap().forEach((category, categoryInsights) -> categoryInsights.forEach((insightName, insightElement) -> {
            insightElement.fix();
            MessageUtil.sendMM(commandSender, NeoPerformance.getLanguageManager().getString("insights.fixed", null) + " " + insightName);
        }));
        MessageUtil.sendMM(commandSender, NeoPerformance.getLanguageManager().getString("insights.fixAutomaticDone", null));
    }
}
