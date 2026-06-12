package com.neomechanical.neoperformance.moderation;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.config.ChatModerationSettings;
import com.neomechanical.neoperformance.utils.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class ChatModerationListener implements Listener {
    private final NeoPerformance plugin;
    private final ChatModerationActionExecutor actionExecutor = new ChatModerationActionExecutor();

    public ChatModerationListener(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("neoperformance.chatmoderation.bypass")) {
            return;
        }

        ChatModerationSettings settings = plugin.getDataManager().chatModeration();
        if (!settings.isEnabled()) {
            return;
        }

        if (settings.getApi().getApiKey().trim().isEmpty()) {
            return;
        }

        boolean flagged = plugin.getChatModerationCoordinator().isMessageFlagged(player, event.getMessage(), settings);
        if (!flagged) {
            return;
        }

        event.setCancelled(true);
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            actionExecutor.execute(player, settings.getActions());
            Logger.info("Chat moderation flagged message from " + player.getName() + " and executed " + settings.getActions().size() + " action(s).");
        });
    }
}
