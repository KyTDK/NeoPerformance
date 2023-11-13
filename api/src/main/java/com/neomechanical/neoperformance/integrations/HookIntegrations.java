package com.neomechanical.neoperformance.integrations;

import com.neomechanical.neoperformance.integrations.discorsrv.DiscordSRVHook;
import lombok.Getter;

@Getter
public class HookIntegrations {
    private final DiscordSRVHook discordSRVHook;

    public HookIntegrations() {
        this.discordSRVHook = new DiscordSRVHook();
    }
}
