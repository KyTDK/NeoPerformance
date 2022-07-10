package com.neomechanical.neoperformance.utils;

import com.neomechanical.neoperformance.NeoPerformance;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;


public class ActionBar {

    public void sendToPlayer(Player p, String text) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(text));
    }

    public void SendComponentToPlayer(Player player, String string) {
        var mm = MiniMessage.miniMessage();
        Component parsed = mm.deserialize(string);
        Audience audience = NeoPerformance.adventure().player(player);
        audience.sendActionBar(parsed);
    }
}