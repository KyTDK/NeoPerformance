package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.NeoPerformance;

public class RegisterCommands {
    public static void register(NeoPerformance plugin) {
        CommandManager commandManager = new CommandManager();
        commandManager.setErrorNotPlayer(plugin.getLanguageManager().getString("commandGeneric.errorNotPlayer", null));
        commandManager.setErrorNoPermission(plugin.getLanguageManager().getString("commandGeneric.errorNoPermission", null));
        commandManager.setErrorCommandNotFound(plugin.getLanguageManager().getString("commandGeneric.errorCommandNotFound", null));
        commandManager.registerMainCommand(new MainCommand());
        commandManager.init(plugin);
    }
}
