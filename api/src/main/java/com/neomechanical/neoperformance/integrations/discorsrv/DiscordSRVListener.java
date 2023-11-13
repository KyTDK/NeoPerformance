package com.neomechanical.neoperformance.integrations.discorsrv;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordReadyEvent;
import github.scarsz.discordsrv.util.DiscordUtil;

public class DiscordSRVListener {

    @Subscribe
    public void discordReadyEvent(DiscordReadyEvent event) {
        DiscordUtil.getJda().addEventListener(new JDAListener());
        NeoUtils.getNeoUtilities().getFancyLogger().info("Chatting on Discord with " + DiscordUtil.getJda().getUsers().size() + " users!");
    }
}