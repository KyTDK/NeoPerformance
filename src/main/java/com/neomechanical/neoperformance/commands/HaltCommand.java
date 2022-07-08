package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.performanceOptimiser.utils.Tps;
import com.neomechanical.neoperformance.utils.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.List;

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

    public HaltCommand(Plugin plugin) {
    }

    @Override
    public void perform(CommandSender player, String[] args) {
        tweakDataManager.toggleManualHalt();
        if (tweakDataManager.isManualHalt()) {
            player.sendMessage(MessageUtil.color("&c&lServer has been halted"));
        } else {
            player.sendMessage(MessageUtil.color("&a&lServer has been resumed"));
        }
    }

    public List<String> tabSuggestions() {
        return null;
    }
}
