package com.neomechanical.neoperformance.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;


public class ActionBar {
   
    public void sendToPlayer(Player p,String text) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(text));
    }
   


}