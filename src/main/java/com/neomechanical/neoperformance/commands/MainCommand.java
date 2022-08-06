package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.utils.messages.Messages;
import com.neomechanical.neoutils.commandManager.Command;
import com.neomechanical.neoutils.messages.MessageUtil;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class MainCommand extends Command implements PerformanceConfigurationSettings {
    private final NeoPerformance plugin = NeoPerformance.getInstance();

    @Override
    public String getName() {
        return "neoperformance";
    }

    @Override
    public String getDescription() {
        return "Main command to view server information";
    }

    @Override
    public String getSyntax() {
        return "/np";
    }

    @Override
    public String getPermission() {
        return "neoperformance.admin";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    private static final MessageUtil messageUtil = new MessageUtil(NeoPerformance.adventure());
    @Override
    public void perform(CommandSender player, String[] args) {
        messageUtil.neoComponentMessage()
                .addComponent(plugin.getLanguageManager().getString("main.isServerHalted", null))
                .addComponent(plugin.getLanguageManager().getString("main.serverTps", null))
                .addComponent(plugin.getLanguageManager().getString("main.serverHaltsAt", null))
                .addComponent(plugin.getLanguageManager().getString("main.playerCount", null));
        if (getVisualData().getShowPluginUpdateInMain()) {
            messageUtil.addComponent(plugin.getLanguageManager().getString("main.upToDate", null));
        }
        messageUtil.sendNeoComponentMessage(player, Messages.MAIN_PREFIX, Messages.MAIN_SUFFIX);
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