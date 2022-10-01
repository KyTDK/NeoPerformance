package com.neomechanical.neoperformance.commands.scheduling;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class ScheduleCreateCommand extends Command {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a schedule";
    }

    @Override
    public String getSyntax() {
        return "/np schedule create";
    }

    @Override
    public String getPermission() {
        return "neoperformance.schedule.create";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        //DO LOGIC
    }

    @Override
    public List<String> tabSuggestions() {
        return null;
    }

    @Override
    public Map<String, List<String>> mapSuggestions() {
        return null;
    }
}
