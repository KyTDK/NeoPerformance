package com.neomechanical.neoperformance.commands.insights;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.modules.insight.InsightManager;
import com.neomechanical.neoperformance.performance.modules.insight.elements.InsightElement;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsightsFixCommand extends Command {
    @Override
    public String getName() {
        return "fix";
    }

    @Override
    public String getPermission() {
        return "neoperformance.insight.fix";
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
        if (strings.length < 3) {
            MessageUtil.sendMM(commandSender, NeoPerformance.getLanguageManager().getString("commandGeneric.errorInvalidSyntax", null));
            return;
        }
        String categoryObtained = strings[2];
        if (categoryObtained.equals("all")) {
            HashMap<String, HashMap<String, InsightElement<?>>> insightMap = new InsightManager().getInsightsMap();

            boolean fixedElement = false;
            insightMap.forEach((category, categoryInsights) ->
                    categoryInsights.entrySet().stream()
                            .filter(entry -> entry.getValue().isAutomatic)
                            .forEach(entry -> {
                                InsightElement<?> insightElement = entry.getValue();
                                insightElement.fix((Player) commandSender);
                                MessageUtil.sendMM(commandSender, NeoPerformance.getLanguageManager().getString("insights.fixed", null) + " " + entry.getKey());
                            })
            );
            if (!fixedElement) {
                MessageUtil.sendMM(commandSender, NeoPerformance.getLanguageManager().getString("insights.noElements", null));
                return;
            }
            MessageUtil.sendMM(commandSender, NeoPerformance.getLanguageManager().getString("insights.fixAutomaticDone", null));
            return;
        }
        if (strings.length < 4) {
            MessageUtil.sendMM(commandSender, NeoPerformance.getLanguageManager().getString("commandGeneric.errorInvalidSyntax", null));
            return;
        }
        String element = strings[3];
        InsightManager insightManager = new InsightManager();
        insightManager.getInsight(categoryObtained, element).fix((Player) commandSender);
        MessageUtil.sendMM(commandSender, NeoPerformance.getLanguageManager().getString("insights.fixAutomaticDone", null));
    }

    @Override
    public Map<String, List<String>> mapSuggestions() {
        Map<String, List<String>> map = new HashMap<>();
        InsightManager insightManager = new InsightManager();
        map.put("all", new ArrayList<>());
        insightManager.getInsightsMap().forEach((category, categoryInsights) -> {
            List<String> insightNames = new ArrayList<>();
            categoryInsights.forEach((insightName, insightElement) -> insightNames.add(insightName));
            map.put(category, insightNames);
        });

        return map;
    }

}
