package com.neomechanical.neoperformance.commands.insights;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.insight.InsightManager;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsightsFixCommand extends Command {
    public InsightsFixCommand() {
        this.addSubcommand(new InsightsFixAllCommand());
    }

    @Override
    public String getName() {
        return "fix";
    }

    @Override
    public String getDescription() {
        return "Run automatic or suggest fixes for insight";
    }

    @Override
    public String getSyntax() {
        return "/np insights fix [category] [insight name]";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        String category = strings[2];
        String element = strings[3];
        InsightManager insightManager = new InsightManager();
        insightManager.getInsight(category, element).fix();
        MessageUtil.sendMM(commandSender, NeoPerformance.getLanguageManager().getString("insights.fixAutomaticDone", null));
    }

    @Override
    public Map<String, List<String>> mapSuggestions() {
        Map<String, List<String>> map = new HashMap<>();
        InsightManager insightManager = new InsightManager();

        insightManager.getInsightsMap().forEach((category, categoryInsights) -> {
            List<String> insightNames = new ArrayList<>();
            categoryInsights.forEach((insightName, insightElement) -> {
                insightNames.add(insightName);
            });
            map.put(category, insightNames);
        });

        return map;
    }

}
