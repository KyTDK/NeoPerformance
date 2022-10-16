package com.neomechanical.neoperformance.commands.scheduling;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoconfig.neoutils.commands.Flags;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class ScheduleCreateCommand extends Command {
    Flags flags = new Flags();
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
        Map<String, List<String>> accumulatedFlagMap = flags
                .addFlag("-test", (sender, args) -> Bukkit.broadcastMessage(args.toString()))
                .addFlag("-dude", (sender, args) -> Bukkit.broadcastMessage(args.toString()))
                .addFlag("-cool", (sender, args) -> Bukkit.broadcastMessage(args.toString()))
                .parseFlags(commandSender, strings);
        Bukkit.broadcastMessage(accumulatedFlagMap.toString());
    }

    @Override
    public List<String> tabSuggestions() {
        return flags.tabSuggestions();
    }

    @Override
    public Map<String, List<String>> mapSuggestions() {
        return null;
    }
}
