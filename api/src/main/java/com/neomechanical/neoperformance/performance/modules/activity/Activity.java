package com.neomechanical.neoperformance.performance.modules.activity;

import com.github.retrooper.packetevents.PacketEvents;

public class Activity {
    public void activity() {

    }

    public void register() {
        PacketEvents.getAPI().getEventManager().registerListener(new ActivityListener());
    }
}
