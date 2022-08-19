package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.utils.messages.Messages;
import com.neomechanical.neoutils.commands.CommandBuilder;
import com.neomechanical.neoutils.commands.easyCommands.EasyHelpCommand;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;

public class RegisterCommands {
    public static void register() {
        new CommandBuilder(new MainCommand())
                .setErrorNotPlayer(() -> getLanguageManager().getString("commandGeneric.errorNotPlayer", null))
                .setErrorNoPermission(() -> getLanguageManager().getString("commandGeneric.errorNoPermission", null))
                .setErrorCommandNotFound(() -> getLanguageManager().getString("commandGeneric.errorCommandNotFound", null))
                .setAliases("np", "performance")
                .addSubcommand(new EasyHelpCommand("neoperformance", "/np help", "See the help menu",
                        "neoperformance.help", false, Messages.MAIN_PREFIX, Messages.MAIN_SUFFIX))
                .addSubcommand(new ReloadCommand())
                .addSubcommand(new BypassCommand())
                .addSubcommand(new BypassCommand())
                .addSubcommand(new ChunksCommand())
                .addSubcommand(new HaltCommand())
                .addSubcommand(new SmartClearCommand())
                .addSubcommand(new ConfigCommand())
                .register();
    }
}
