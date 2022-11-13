package com.neomechanical.neoperformance.commands.smartReport;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.smart.smartReport.SmartReport;
import org.bukkit.command.CommandSender;

public class SmartReportCommand extends Command {
    private final NeoPerformance plugin;

    public SmartReportCommand(NeoPerformance plugin) {
        super.addSubcommand(new SmartReportSubjectsCommand(plugin));
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "report";
    }

    @Override
    public String getDescription() {
        return "Generate a report of your severs overall performance";
    }

    @Override
    public String getSyntax() {
        return "/np report";
    }

    @Override
    public String getPermission() {
        return "neoperformance.report";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {
        new SmartReport(plugin).getPerformanceReportOverview().sendReport(commandSender);
    }
}
