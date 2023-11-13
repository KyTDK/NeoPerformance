package com.neomechanical.neoperformance.integrations.discorsrv;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import net.dv8tion.jda.api.events.guild.GuildUnavailableEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class JDAListener extends ListenerAdapter {
    @Override // we can use any of JDA's events through ListenerAdapter, just by overriding the methods
    public void onGuildUnavailable(@NotNull GuildUnavailableEvent event) {
        NeoUtils.getNeoUtilities().getFancyLogger().severe("Oh no " + event.getGuild().getName() + " went unavailable :(");
    }

}