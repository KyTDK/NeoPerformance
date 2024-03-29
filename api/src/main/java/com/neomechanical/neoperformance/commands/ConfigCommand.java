package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoconfig.menu.ConfigMenu;
import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoperformance.NeoPerformance;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class ConfigCommand extends Command {
    private final NeoPerformance plugin;

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

    public ConfigCommand(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    @Override
    public void perform(CommandSender player, String[] args) {
        Player playerAsPlayer = (Player) player;
        ConfigMenu configMenu = new ConfigMenu(plugin);
        configMenu.onComplete((playerAsAuthor, text) -> plugin.reload())
                .setPluginEditing(plugin)
                .permission("neoperformance.config", () -> NeoPerformance.getLanguageManager().getString("commandGeneric.errorNoPermission", null))
                .open(playerAsPlayer);
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
