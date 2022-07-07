package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.NeoPerformance;

public class RegisterCommands {
    public static CommandManager register(NeoPerformance plugin) {
        CommandManager commandManager = new CommandManager();
        commandManager.init(plugin);
        return commandManager;
    }
}
