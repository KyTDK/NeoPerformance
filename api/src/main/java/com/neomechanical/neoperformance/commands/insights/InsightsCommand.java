package com.neomechanical.neoperformance.commands.insights;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
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
        this.addSubcommand(new InsightsFixCommand());
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
        insights.openInsights(commandSender);
    }
}
