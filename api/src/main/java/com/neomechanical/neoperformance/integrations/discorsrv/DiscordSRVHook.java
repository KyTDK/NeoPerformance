package com.neomechanical.neoperformance.integrations.discorsrv;

import github.scarsz.discordsrv.DiscordSRV;
import net.dv8tion.jda.api.entities.TextChannel;

public class DiscordSRVHook {

    public DiscordSRVHook() {
        DiscordSRV.api.subscribe(new DiscordSRVListener());
    }

    public void sendMessage(String message) {
        TextChannel textChannel = DiscordSRV.getPlugin().getMainTextChannel();

        if (textChannel != null) {
            textChannel.sendMessage(message).complete();
        }
    }

}
