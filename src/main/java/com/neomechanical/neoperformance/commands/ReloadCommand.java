package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoutils.commandManager.SubCommand;
import com.neomechanical.neoutils.messages.MessageUtil;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class ReloadCommand extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload the plugin's config file";
    }

    @Override
    public String getSyntax() {
        return "/np reload";
    }

    @Override
    public String getPermission() {
        return "neoperformance.reload";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    private final NeoPerformance plugin = NeoPerformance.getInstance();
    private final MessageUtil messageUtil = new MessageUtil(NeoPerformance.adventure());

    @Override
    public void perform(CommandSender player, String[] args) {
        NeoPerformance.reloadTweakDataManager();
        messageUtil.sendMM(player, plugin.getLanguageManager().getString("reload.onReload", null));
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
