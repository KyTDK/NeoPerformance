package com.neomechanical.neoperformance.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        public MessageUtil addMessage(String msg) {
            neoMessageArray.add(msg);
            return this;
        }
        public void sendMessage(Player player) {
            addMessage("&7&l&m                                                         ");
            for (String msg : neoMessageArray) {
                player.sendMessage(color(msg));
            }
            neoMessageArray.clear();
        }
        public MessageUtil neoMessage() {
            addMessage("&7&l&m                   &a&lNeoPerformance&7&l&m                   ");
            return this;
        }
}
