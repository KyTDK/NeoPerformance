package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.performanceOptimiser.utils.Tps;
import com.neomechanical.neoutils.commandManager.SubCommand;
import com.neomechanical.neoutils.messages.MessageUtil;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

import static com.neomechanical.neoutils.NeoUtils.getLanguageManager;

public class HaltCommand extends SubCommand implements Tps {
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

    @Override
    public void perform(CommandSender player, String[] args) {
        DATA_MANAGER.toggleManualHalt();
        if (DATA_MANAGER.isManualHalt()) {
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
