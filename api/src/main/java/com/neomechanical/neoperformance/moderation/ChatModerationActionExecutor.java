package com.neomechanical.neoperformance.moderation;

import com.neomechanical.neoperformance.config.ChatModerationAction;
import com.neomechanical.neoperformance.config.ChatModerationActionType;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatModerationActionExecutor {
    private static final String DEFAULT_MUTE_COMMAND = "mute %PLAYER% %DURATION%s %REASON%";
    private static final String DEFAULT_TIMEOUT_COMMAND = "tempmute %PLAYER% %DURATION%s %REASON%";
    private static final String DEFAULT_GIVE_ROLE_COMMAND = "lp user %PLAYER% parent add %ROLE%";
    private static final String DEFAULT_TAKE_ROLE_COMMAND = "lp user %PLAYER% parent remove %ROLE%";
    private static final String DEFAULT_TEMP_ROLE_COMMAND = "lp user %PLAYER% parent addtemp %ROLE% %DURATION%s";

    public void execute(Player player, List<ChatModerationAction> actions) {
        for (ChatModerationAction action : actions) {
            executeAction(player, action);
        }
    }

    private void executeAction(Player player, ChatModerationAction action) {
        switch (action.getType()) {
            case CLEAR_CHAT -> clearChat();
            case KICK -> player.kickPlayer(action.getReason());
            case BAN -> banPlayer(player, action.getReason());
            case MUTE -> runCommand(player, action, fallbackOr(action.getCommand(), DEFAULT_MUTE_COMMAND));
            case TIMEOUT -> runCommand(player, action, fallbackOr(action.getCommand(), DEFAULT_TIMEOUT_COMMAND));
            case GIVE_ROLE -> runCommand(player, action, fallbackOr(action.getCommand(), DEFAULT_GIVE_ROLE_COMMAND));
            case TAKE_ROLE -> runCommand(player, action, fallbackOr(action.getCommand(), DEFAULT_TAKE_ROLE_COMMAND));
            case TEMP_ROLE -> runCommand(player, action, fallbackOr(action.getCommand(), DEFAULT_TEMP_ROLE_COMMAND));
            case COMMAND -> {
                if (!action.getCommand().isEmpty()) {
                    runCommand(player, action, action.getCommand());
                }
            }
        }
    }

    private void clearChat() {
        for (int i = 0; i < 90; i++) {
            Bukkit.broadcastMessage(" ");
        }
    }

    private void banPlayer(Player player, String reason) {
        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), reason, null, "NeoPerformance");
        player.kickPlayer(reason);
    }

    private void runCommand(Player player, ChatModerationAction action, String command) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String hydratedCommand = hydrate(command, player, action);
        Bukkit.dispatchCommand(console, hydratedCommand.startsWith("/") ? hydratedCommand.substring(1) : hydratedCommand);
    }

    private String hydrate(String command, Player player, ChatModerationAction action) {
        return command
                .replace("%PLAYER%", player.getName())
                .replace("%UUID%", player.getUniqueId().toString())
                .replace("%ROLE%", action.getRole())
                .replace("%DURATION%", String.valueOf(action.getDurationSeconds()))
                .replace("%REASON%", action.getReason());
    }

    private String fallbackOr(String maybeEmpty, String fallback) {
        return maybeEmpty == null || maybeEmpty.trim().isEmpty() ? fallback : maybeEmpty;
    }
}
