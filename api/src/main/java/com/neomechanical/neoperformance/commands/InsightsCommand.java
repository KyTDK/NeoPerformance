package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.insight.InsightManager;
import com.neomechanical.neoperformance.performance.insight.Insights;
import org.bukkit.command.CommandSender;

public class InsightsCommand extends Command {
    public InsightsCommand() {
        this.addSubcommand(new Command() {
            @Override
            public String getName() {
                return "sources";
            }

            @Override
            public String getDescription() {
                return "Sources used to determine what insights to provide";
            }

            @Override
            public String getSyntax() {
                return "/np insights sources";
            }

            @Override
            public boolean playerOnly() {
                return false;
            }

            @Override
            public void perform(CommandSender commandSender, String[] strings) {
                MessageUtil.sendMM(commandSender, "<gray>• https://www.spigotmc.org/wiki/reducing-lag/");
            }
        });
        this.addSubcommand(new Command() {
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
                NeoUtils.getNeoUtilities().getFancyLogger().info(category);
                InsightManager insightManager = new InsightManager();
                insightManager.getInsight(category, element).fix();
                MessageUtil.sendMM(commandSender, NeoPerformance.getLanguageManager().getString("insights.fixAutomaticDone", null));
            }
        });
    }

    @Override
    public String getName() {
        return "insights";
    }

    @Override
    public String getDescription() {
        return "Provides insights into what can be done to further optimize your server";
    }

    @Override
    public String getSyntax() {
        return "/np insights";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        Insights insights = new Insights();
        insights.sendInsights(commandSender);
    }
}
