package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoutils.commandManager.CommandManager;

public class RegisterCommands {
    public static void register(NeoPerformance plugin) {
        CommandManager commandManager = new CommandManager();
        commandManager.registerSubCommand(new BypassCommand());
        commandManager.setErrorNotPlayer(plugin.getLanguageManager().getString("commandGeneric.errorNotPlayer", null));
        commandManager.setErrorNoPermission(plugin.getLanguageManager().getString("commandGeneric.errorNoPermission", null));
        commandManager.setErrorCommandNotFound(plugin.getLanguageManager().getString("commandGeneric.errorCommandNotFound", null));
        commandManager.registerMainCommand(new MainCommand());
        commandManager.registerSubCommand(new HelpCommand(commandManager));
        commandManager.registerSubCommand(new ReloadCommand());
        commandManager.registerSubCommand(new BypassCommand());
        commandManager.registerSubCommand(new ChunksCommand());
        commandManager.registerSubCommand(new HaltCommand());
        commandManager.registerSubCommand(new SmartClearCommand());
        commandManager.init(plugin, "neoperformance");
    }
}
