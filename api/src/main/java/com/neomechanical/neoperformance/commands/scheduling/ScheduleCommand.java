package com.neomechanical.neoperformance.commands.scheduling;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.utils.messages.Messages;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class ScheduleCommand extends Command {
    public ScheduleCommand() {
        super.addSubcommand(new ScheduleCreateCommand());
    }

    @Override
    public String getName() {
        return "schedule";
    }

    @Override
    public String getDescription() {
        return "Schedule tasks to run";
    }

    @Override
    public String getSyntax() {
        return "/np schedule";
    }

    @Override
    public String getPermission() {
        return "neoperformance.schedule";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        MessageUtil messageUtil = new MessageUtil();
        messageUtil.neoComponentMessage()
                .addComponent("/np schedule create")
                .sendNeoComponentMessage(commandSender, Messages.MAIN_PREFIX, Messages.MAIN_SUFFIX);
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
