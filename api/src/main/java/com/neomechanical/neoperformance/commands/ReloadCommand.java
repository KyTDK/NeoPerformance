package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;

public class ReloadCommand extends Command {
    private final NeoPerformance plugin;
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

    public ReloadCommand(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    @Override
    public void perform(CommandSender player, String[] args) {
        plugin.reload();
        MessageUtil.sendMM(player, getLanguageManager().getString("reload.onReload", null));
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
