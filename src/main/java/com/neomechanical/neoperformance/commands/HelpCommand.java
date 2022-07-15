package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.utils.MessageUtil;
import com.neomechanical.neoperformance.utils.Pagination;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

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
        messageUtil.neoComponentMessage();
        int page = 1;
        if (args.length == 2) {
            if (Integer.getInteger(args[1]) == null) {
                MessageUtil.sendMM(player, plugin.getLanguageManager().getString("commandGeneric.errorInvalidSyntax", null));
                return;
            }
            page = Integer.getInteger(args[1]);
        }
        List<SubCommand> pageList = Pagination.getPage(commandManager.getSubcommands(), page, 10);
        if (pageList == null) {
            MessageUtil.sendMM(player, plugin.getLanguageManager().getString("commandGeneric.errorInvalidSyntax", null));
            return;
        }
        for (SubCommand subCommand : pageList) {
            messageUtil.addComponent("  <gray><bold>" + subCommand.getSyntax() + "</bold> - " + subCommand.getDescription());
        }
        messageUtil.sendNeoComponentMessage(player);
    }

    @Override
    public List<String> tabSuggestions() {
        return null;
    }

    @Override
    public Map<String, List<String>> mapSuggestions() {
        return null;
    }
}
