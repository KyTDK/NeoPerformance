package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.utils.MessageUtil;
import com.neomechanical.neoperformance.utils.Pagination;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand extends SubCommand{
    private final CommandManager commandManager;
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Get a list of commands with their function";
    }

    @Override
    public String getSyntax() {
        return "/np help";
    }

    @Override
    public String getPermission() {
        return "neoperformance.help";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    public HelpCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    private final NeoPerformance plugin = NeoPerformance.getInstance();

    @Override
    public void perform(CommandSender player, String[] args) {
        MessageUtil messageUtil = new MessageUtil();
        messageUtil.neoMessage();
        //TODO PAGINATION
        int page = 0;
        if (args.length == 2) {
            if (Integer.getInteger(args[1]) == null) {
                MessageUtil.sendMM(player, plugin.getLanguageManager().getString("commandGeneric.errorInvalidSyntax", null));
                return;
            }
            page = Integer.getInteger(args[1]);
        }
        List<String> page = Pagination.getPage(commandManager.getSubcommands(), page, 10)
        for (int i = 0; i < commandManager.getSubcommands().size(); i++) {
            messageUtil.addMessage("  &7&l" + commandManager.getSubcommands().get(i).getSyntax() + " - &r&7" + commandManager.getSubcommands().get(i).getDescription());
        }
        messageUtil.sendMessage(player);
    }

    @Override
    public List<String> tabSuggestions() {
        return null;
    }
}
