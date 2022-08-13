package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.performanceOptimiser.utils.Tps;
import com.neomechanical.neoutils.commandManager.SubCommand;
import com.neomechanical.neoutils.messages.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

import static com.neomechanical.neoutils.NeoUtils.getLanguageManager;

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
                MessageUtil.sendMM(player, getLanguageManager().getString("bypass.errorNotPlayer", null));
                return;
            }
            if (DATA_MANAGER.toggleBypass(player)) {
                MessageUtil.sendMM(player, getLanguageManager().getString("bypass.nowBypassing", null));
            } else {
                MessageUtil.sendMM(player, getLanguageManager().getString("bypass.noLongerBypassing", null));
            }
        } else if (args.length == 2) {
            if (!player.hasPermission("neoperformance.bypass.others")) {
                MessageUtil.sendMM(player, getLanguageManager().getString("bypass.errorNoPermissionOthers", null));
                return;
            }
            Player player1 = Bukkit.getPlayer(args[1]);
            if (player1 != null) {
                if (DATA_MANAGER.toggleBypass(player1)) {
                    MessageUtil.sendMM(player, getLanguageManager().getString("bypass.nowBypassingPlayer", player1));
                } else {
                    MessageUtil.sendMM(player, getLanguageManager().getString("bypass.noLongerBypassingPlayer", player1));
                }
            } else {
                MessageUtil.sendMM(player, getLanguageManager().getString("commandGeneric.errorPlayerNotFound", null));
            }
        } else {
            MessageUtil.sendMM(player, getLanguageManager().getString("commandGeneric.errorInvalidSyntax", null));
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
