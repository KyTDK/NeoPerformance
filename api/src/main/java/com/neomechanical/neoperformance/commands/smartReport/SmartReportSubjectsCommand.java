package com.neomechanical.neoperformance.commands.smartReport;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.performance.smart.smartReport.SmartReport;
import org.bukkit.command.CommandSender;

public class SmartReportSubjectsCommand extends Command {
    private final NeoPerformance plugin;
    private final DataManager dataManager;

    public SmartReportSubjectsCommand(NeoPerformance plugin, DataManager dataManager) {
        this.plugin = plugin;
        this.dataManager = dataManager;
    }

    @Override
    public String getName() {
        return "subjects";
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
        return "neoperformance.report.subjects";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {
        new SmartReport(plugin, dataManager).getPerformanceReportSubjects().sendReport(commandSender);
    }
}
