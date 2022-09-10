package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoconfig.neoutils.commands.CommandBuilder;
import com.neomechanical.neoconfig.neoutils.commands.easyCommands.EasyHelpCommand;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.commands.chunks.ChunksCommand;
import com.neomechanical.neoperformance.utils.messages.Messages;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;

public class RegisterCommands {
    private final NeoPerformance plugin;

    public RegisterCommands(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    public void register() {
        new CommandBuilder(plugin, new MainCommand(plugin))
                .setErrorNotPlayer(() -> getLanguageManager().getString("commandGeneric.errorNotPlayer", null))
                .setErrorNoPermission(() -> getLanguageManager().getString("commandGeneric.errorNoPermission", null))
                .setErrorCommandNotFound(() -> getLanguageManager().getString("commandGeneric.errorCommandNotFound", null))
                .setAliases("np", "performance")
                .addSubcommand(new EasyHelpCommand("neoperformance", "/np help", "See the help menu",
                        "neoperformance.help", false, Messages.MAIN_PREFIX, Messages.MAIN_SUFFIX))
                .addSubcommand(new ReloadCommand(plugin))
                .addSubcommand(new BypassCommand(plugin))
                .addSubcommand(new ChunksCommand(plugin))
                .addSubcommand(new HaltCommand(plugin))
                .addSubcommand(new SmartClearCommand(plugin))
                .addSubcommand(new ConfigCommand(plugin))
                .addSubcommand(new SmartReportCommand(plugin))
                .register();
    }
}
