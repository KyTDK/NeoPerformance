package com.neomechanical.neoperformance.utils;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.audience.Audience;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.Component;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;


public class ActionBar {

    public void SendComponentToPlayer(Player player, String string) {
        MiniMessage mm = MiniMessage.miniMessage();
        Component parsed = mm.deserialize(string);
        Audience audience = NeoUtils.getNeoUtilities().getAdventure().player(player);
        audience.sendActionBar(parsed);
    }
}