package com.neomechanical.neoperformance.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract String getPermission();

    public abstract boolean playerOnly();

    //code for the subcommand
    public abstract void perform(CommandSender player, String[] args);

    public abstract List<String> tabSuggestions();

}
