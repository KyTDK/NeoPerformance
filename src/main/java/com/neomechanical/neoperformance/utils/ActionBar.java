package com.neomechanical.neoperformance.utils;

import com.neomechanical.neoperformance.NeoPerformance;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;


public class ActionBar {

    public void SendComponentToPlayer(Player player, String string) {
        var mm = MiniMessage.miniMessage();
        Component parsed = mm.deserialize(string);
        Audience audience = NeoPerformance.adventure().player(player);
        audience.sendActionBar(parsed);
    }
}