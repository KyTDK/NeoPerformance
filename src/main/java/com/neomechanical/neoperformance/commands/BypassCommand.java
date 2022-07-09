package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.performanceOptimiser.utils.Tps;
import com.neomechanical.neoperformance.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
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
        return "/np bypass <player>";
    }

    @Override
    public String getPermission() {
        return "neoperformance.bypass";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public void perform(CommandSender player, String[] args) {
        if (args.length == 1) {
            if (!(player instanceof Player)) {
                player.sendMessage(MessageUtil.color("&c&lYou must be a player to bypass yourself"));
                return;
            }
            if (tweakDataManager.toggleBypass(player)) {
                player.sendMessage(MessageUtil.color("&c&lNow bypassing halt"));
            } else {
                player.sendMessage(MessageUtil.color("&a&lNo longer bypassing halt"));
            }
        } else if (args.length == 2) {
            if (!player.hasPermission("neoperformance.bypass.others")) {
                player.sendMessage(MessageUtil.color("&c&lYou do not have permission to bypass other players"));
                return;
            }
            Player player1 = Bukkit.getPlayer(args[1]);
            if (player1 != null) {
                if (tweakDataManager.toggleBypass(player1)) {
                    player.sendMessage(MessageUtil.color("&c&lNow bypassing halt for " + player1.getName()));
                } else {
                    player.sendMessage(MessageUtil.color("&a&lNo longer bypassing halt for " + player1.getName()));
                }
            } else {
                player.sendMessage(MessageUtil.color("&c&lPlayer not found"));
            }
        } else {
            player.sendMessage(MessageUtil.color("&c&lInvalid syntax - type '/np help' for help"));
        }
    }

    public List<String> tabSuggestions() {
        return null;
    }
}
