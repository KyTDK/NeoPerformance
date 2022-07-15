package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.NeoPerformance;

public class RegisterCommands {
    public static void register(NeoPerformance plugin) {
        CommandManager commandManager = new CommandManager();
        commandManager.init(plugin);
    }
}
