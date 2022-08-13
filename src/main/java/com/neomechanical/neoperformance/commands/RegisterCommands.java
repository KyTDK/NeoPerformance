package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoutils.commandManager.CommandManager;

import static com.neomechanical.neoutils.NeoUtils.getLanguageManager;

public class RegisterCommands {
    public static void register(NeoPerformance plugin) {
        CommandManager commandManager = new CommandManager(plugin, "neoperformance");
        commandManager.registerSubCommand(new BypassCommand());
        commandManager.setErrorNotPlayer(() -> getLanguageManager().getString("commandGeneric.errorNotPlayer", null));
        commandManager.setErrorNoPermission(() -> getLanguageManager().getString("commandGeneric.errorNoPermission", null));
        commandManager.setErrorCommandNotFound(() -> getLanguageManager().getString("commandGeneric.errorCommandNotFound", null));
        commandManager.registerMainCommand(new MainCommand());
        commandManager.registerSubCommand(new HelpCommand(commandManager));
        commandManager.registerSubCommand(new ReloadCommand());
        commandManager.registerSubCommand(new BypassCommand());
        commandManager.registerSubCommand(new ChunksCommand());
        commandManager.registerSubCommand(new HaltCommand());
        commandManager.registerSubCommand(new SmartClearCommand());
        commandManager.registerSubCommand(new ConfigCommand());
    }
}
