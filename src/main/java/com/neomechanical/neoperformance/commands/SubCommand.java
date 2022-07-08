package com.neomechanical.neoperformance.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {

    //name of the subcommand ex. /prank <subcommand> <-- that
    public abstract String getName();

    //ex. "This is a subcommand that let's a shark eat someone"
    public abstract String getDescription();

    //How to use command ex. /prank freeze <player>
    public abstract String getSyntax();

    public abstract String getPermission();

    public abstract boolean playerOnly();

    //code for the subcommand
    public abstract void perform(CommandSender player, String[] args);

    public abstract List<String> tabSuggestions();

}
