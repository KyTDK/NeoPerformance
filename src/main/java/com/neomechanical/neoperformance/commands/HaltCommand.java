package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.managers.TweakDataManager;
import com.neomechanical.neoperformance.utils.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class HaltCommand extends SubCommand {
    private final TweakDataManager tweakDataManager = NeoPerformance.getTweakDataManager();
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

    public HaltCommand(Plugin plugin) {
    }
    @Override
    public void perform(Player player, String[] args) {
        tweakDataManager.toogleManualHalt();
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
