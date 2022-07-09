package com.neomechanical.neoperformance.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public final class MessageUtil {

    /**
     * A utility class for handling Bukkit messages.
     */
    public MessageUtil() {}

        /**
         * Translate an uncolored message.
         *
         * @param msg the msg
         * @return the colored message
         */
        @Contract("null -> null")
        public static String color(String msg) {
            return msg == null ? null : ChatColor.translateAlternateColorCodes('&', msg);
        }

    public static void messageAdmins(String msg) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isOp()) {
                player.sendMessage(color(msg));
            }
        }
    }

    static List<String> neoMessageArray = new ArrayList<>();

    public static void sendBaseMessages(List<BaseComponent[]> msg, Player player) {
        player.sendMessage(color("&7&l&m                   &a&lNeoPerformance&7&l&m                   "));
        for (BaseComponent[] components : msg) {
            player.spigot().sendMessage(components);
        }
        player.sendMessage(color("&7&l&m                                                         "));
    }

    public void sendMessage(Player player) {
        addMessage("&7&l&m                                                         ");
        for (String msg : neoMessageArray) {
            player.sendMessage(color(msg));
        }
        neoMessageArray.clear();
    }

    public void sendMessage(CommandSender player) {
        addMessage("&7&l&m                                                         ");
        for (String msg : neoMessageArray) {
            player.sendMessage(color(msg));
        }
        neoMessageArray.clear();
    }

    public MessageUtil addMessage(String msg) {
        neoMessageArray.add(msg);
        return this;
    }

    public MessageUtil neoMessage() {
        addMessage("&7&l&m                   &a&lNeoPerformance&7&l&m                   ");
        return this;
    }
}
