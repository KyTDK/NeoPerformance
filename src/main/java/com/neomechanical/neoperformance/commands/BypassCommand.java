package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.performanceOptimiser.utils.Tps;
import com.neomechanical.neoperformance.utils.MessageUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class BypassCommand extends SubCommand implements Tps {
    @Override
    public String getName() {
        return "bypass";
    }

    @Override
    public String getDescription() {
        return "Bypass halt, type this command to toggle";
    }

    @Override
    public String getSyntax() {
        return "/np bypass";
    }

    @Override
    public String getPermission() {
        return "neoperformance.bypass";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (tweakDataManager.toggleBypass(player)) {
            player.sendMessage(MessageUtil.color("&c&lNow bypassing halt"));
        } else {
            player.sendMessage(MessageUtil.color("&a&lNo longer bypassing halt"));
        }
    }

    public List<String> tabSuggestions() {
        return null;
    }
}
