package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoconfig.menu.ConfigMenu;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoutils.commandManager.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class ConfigCommand extends SubCommand {
    @Override
    public String getName() {
        return "config";
    }

    @Override
    public String getDescription() {
        return "Show the config file in an interactive GUI";
    }

    @Override
    public String getSyntax() {
        return "/np config";
    }

    @Override
    public String getPermission() {
        return "neoperformance.config";
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public void perform(CommandSender player, String[] args) {
        Player playerAsPlayer = (Player) player;
        ConfigMenu configMenu = new ConfigMenu(NeoPerformance.getInstance());
        configMenu.onComplete((playerAsAuthor, text) -> NeoPerformance.reload())
                .permission("neoperformance.config")
                .open(playerAsPlayer, NeoPerformance.getInstance());
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
