package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;

public class HaltCommand extends Command {
    private final NeoPerformance plugin;

    @Override
    public String getName() {
        return "halt";
    }

    @Override
    public String getDescription() {
        return "Manually halt the server, type this command to toggle";
    }

    @Override
    public String getSyntax() {
        return "/np halt";
    }

    @Override
    public String getPermission() {
        return "neoperformance.halt";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    public HaltCommand(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    @Override
    public void perform(CommandSender player, String[] args) {
        plugin.getDataManager().toggleManualHalt();
        if (plugin.getDataManager().isManualHalt()) {
            MessageUtil.sendMM(player, getLanguageManager().getString("halt.toggleHaltOn", null));
        } else {
            MessageUtil.sendMM(player, getLanguageManager().getString("halt.toggleHaltOff", null));
        }
    }

    public List<String> tabSuggestions() {
        return null;
    }

    @Override
    public Map<String, List<String>> mapSuggestions() {
        return null;
    }
}
