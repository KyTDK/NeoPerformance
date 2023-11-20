package com.neomechanical.neoperformance.commands.smartReport;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.performance.modules.smartReport.SmartReport;
import org.bukkit.command.CommandSender;

public class SmartReportCommand extends Command {
    private final NeoPerformance plugin;
    private final DataManager dataManager;

    public SmartReportCommand(NeoPerformance plugin, DataManager dataManager) {
        super.addSubcommand(new SmartReportSubjectsCommand(plugin, dataManager));
        this.plugin = plugin;
        this.dataManager = dataManager;
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
        new SmartReport(plugin, dataManager).getPerformanceReportOverview().sendReport(commandSender);
    }
}
