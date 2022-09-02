package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.utils.messages.Messages;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;

public class MainCommand extends Command {
    private final NeoPerformance plugin;

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

    public MainCommand(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    private static final MessageUtil messageUtil = new MessageUtil();

    @Override
    public void perform(CommandSender player, String[] args) {
        messageUtil.neoComponentMessage()
                .addComponent(getLanguageManager().getString("main.isServerHalted", null))
                .addComponent(getLanguageManager().getString("main.serverTps", null))
                .addComponent(getLanguageManager().getString("main.serverHaltsAt", null))
                .addComponent(getLanguageManager().getString("main.playerCount", null));
        if (plugin.getDataManager().getVisualData().getShowPluginUpdateInMain()) {
            messageUtil.addComponent(getLanguageManager().getString("main.upToDate", null));
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
