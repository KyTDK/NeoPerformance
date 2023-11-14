package com.neomechanical.neoperformance.integrations.discorsrv;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.DiscordUtil;
import github.scarsz.discordsrv.util.PlaceholderUtil;

public class DiscordSRVHook {

    public DiscordSRVHook() {
        DiscordSRV.api.subscribe(new DiscordSRVListener());
    }

    public void sendMessage(String message) {
        TextChannel textChannel = DiscordSRV.getPlugin().getMainTextChannel();

        if (textChannel != null) {
            DiscordUtil.queueMessage(
                    DiscordSRV.getPlugin().getOptionalTextChannel("status"),
                    PlaceholderUtil.replacePlaceholdersToDiscord(message),
                    true
            );
        }
    }

}
