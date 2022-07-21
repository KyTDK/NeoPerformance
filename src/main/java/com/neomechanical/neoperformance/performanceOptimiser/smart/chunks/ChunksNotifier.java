package com.neomechanical.neoperformance.performanceOptimiser.smart.chunks;

import com.neomechanical.neoperformance.utils.messages.MessageUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChunksNotifier {
    public static void sendChatData(List<Chunk> chunks, World world, Player playerAsPlayer) {
        ArrayList<BaseComponent[]> messages = new ArrayList<>();
        for (Chunk chunk : chunks) {
            ChatColor colour;
            if (chunk.getEntities().length >= 1000) {
                colour = ChatColor.DARK_RED;
            } else if (chunk.getEntities().length >= 500) {
                colour = ChatColor.RED;
            } else if (chunk.getEntities().length >= 100) {
                colour = ChatColor.YELLOW;
            } else {
                colour = ChatColor.GREEN;
            }
            Entity entityToLocate = chunk.getEntities()[0];
            Location location = new Location(world, Math.round(entityToLocate.getLocation().getX()), Math.round(entityToLocate.getLocation().getY()),
                    Math.round(entityToLocate.getLocation().getZ()));
            ComponentBuilder bc = new ComponentBuilder("  Chunk: ").color(colour).bold(true)
                    .append(location.getX() + " " + location.getZ()).color(colour).bold(false)
                    .append(" - Entities: ").color(colour).bold(true)
                    .append(String.valueOf(chunk.getEntities().length)).color(colour).bold(false);
            if (world == null) {
                bc.append(" - World: ").bold(true);
                bc.append(chunk.getWorld().getName()).color(colour).bold(false);
            }
            for (BaseComponent component : bc.getParts()) {
                component.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.RUN_COMMAND,
                        "/minecraft:execute in " + chunk.getWorld().getKey()
                                + " run tp " + playerAsPlayer.getName() + " " + location.getX()
                                + " " + location.getY() + " " + location.getZ()));
                component.setHoverEvent(new net.md_5.bungee.api.chat.HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to teleport to chunk").create()));
            }
            messages.add(bc.create());
        }
        MessageUtil.sendBaseMessages(messages, playerAsPlayer);
    }
}
