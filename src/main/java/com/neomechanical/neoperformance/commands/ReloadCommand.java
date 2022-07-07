package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigCore;
import com.neomechanical.neoperformance.utils.MessageUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadCommand extends SubCommand{

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
    public void perform(Player player, String[] args) {
        NeoPerformance.reloadTweakDataManager();
        player.sendMessage(MessageUtil.color("&a&lConfig file reloaded"));
    }

    @Override
    public List<String> tabSuggestions() {
        return null;
    }
}
