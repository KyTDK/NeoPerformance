package com.neomechanical.neoperformance.performance.modules.activity;

import com.github.retrooper.packetevents.event.PacketHandler;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerCommon;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.neomechanical.neoconfig.neoutils.NeoUtils;
import org.bukkit.entity.Player;

public class ActivityListener extends PacketListenerCommon implements PacketListener {
    @PacketHandler
    public void onPacketReceive(PacketReceiveEvent event) {
        Player player = (Player) event.getPlayer();
        if (player == null) {
            return;
        }
        NeoUtils.getNeoUtilities().getFancyLogger().info(String.valueOf(event.getPacketType()));
    }
}
