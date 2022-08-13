package com.neomechanical.neoperformance.utils;

import com.neomechanical.kyori.adventure.audience.Audience;
import com.neomechanical.kyori.adventure.text.Component;
import com.neomechanical.kyori.adventure.text.minimessage.MiniMessage;
import com.neomechanical.neoutils.NeoUtils;
import org.bukkit.entity.Player;


public class ActionBar {

    public void SendComponentToPlayer(Player player, String string) {
        var mm = MiniMessage.miniMessage();
        Component parsed = mm.deserialize(string);
        Audience audience = NeoUtils.adventure().player(player);
        audience.sendActionBar(parsed);
    }
}