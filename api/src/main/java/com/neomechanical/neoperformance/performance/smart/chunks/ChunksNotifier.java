package com.neomechanical.neoperformance.performance.smart.chunks;

import com.neomechanical.neoperformance.utils.messages.Messages;
import com.neomechanical.neoutils.kyori.adventure.text.Component;
import com.neomechanical.neoutils.kyori.adventure.text.TextComponent;
import com.neomechanical.neoutils.kyori.adventure.text.event.ClickEvent;
import com.neomechanical.neoutils.kyori.adventure.text.event.HoverEvent;
import com.neomechanical.neoutils.kyori.adventure.text.format.NamedTextColor;
import com.neomechanical.neoutils.kyori.adventure.text.format.TextDecoration;
import com.neomechanical.neoutils.messages.MessageUtil;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class ChunksNotifier {
    public static void sendChatData(List<Chunk> chunks, World world, CommandSender player) {
        TextComponent.Builder bc = getChatData(chunks, world, player);
        MessageUtil messageUtil = new MessageUtil();
        messageUtil.addComponent(bc.build());
        messageUtil.sendNeoComponentMessage(player, Messages.MAIN_PREFIX, Messages.MAIN_SUFFIX);
    }

    public static TextComponent.Builder getChatData(List<Chunk> chunks, World world, CommandSender player) {
        TextComponent.Builder bc = Component.text();
        for (Chunk chunk : chunks) {
            NamedTextColor color;
            int entitySize = chunk.getEntities().length;
            if (entitySize >= 1000) {
                color = NamedTextColor.DARK_RED;
            } else if (entitySize >= 500) {
                color = NamedTextColor.RED;
            } else if (entitySize >= 100) {
                color = NamedTextColor.YELLOW;
            } else {
                color = NamedTextColor.GREEN;
            }
            TextComponent.Builder message = Component.text();
            Entity[] entities = chunk.getEntities();
            if (entities.length > 0) {
                Entity entityToLocate = entities[0];
                Location location = new Location(world, Math.round(entityToLocate.getLocation().getX()), Math.round(entityToLocate.getLocation().getY()),
                        Math.round(entityToLocate.getLocation().getZ()));
                message.append(Component.text("  Chunk: ").color(color).decorate(TextDecoration.BOLD))
                        .append(Component.text(location.getX() + " " + location.getZ()).color(color).decorate(TextDecoration.BOLD))
                        .append(Component.text(" - Entities: ").color(color).decorate(TextDecoration.BOLD))
                        .append(Component.text(String.valueOf(chunk.getEntities().length)).color(color).decorate(TextDecoration.BOLD));
                if (world == null) {
                    message.append(Component.text(" - World: ").color(color).decorate(TextDecoration.BOLD));
                    message.append(Component.text(chunk.getWorld().getName()).color(color).decorate(TextDecoration.BOLD));
                }
                message.color(color).decorate(TextDecoration.BOLD);
                //Append new line
                if (chunks.indexOf(chunk) != chunks.size() - 1) {
                    message.append(Component.newline());
                }
                if (player instanceof Player) {
                    message.clickEvent(ClickEvent.runCommand(
                            "/np chunks " + chunk.getWorld().getName()
                                    + " " + location.getX()
                                    + " " + location.getY() + " " + location.getZ() + " " + player.getName()));
                    message.hoverEvent(HoverEvent.showText(Component.text("Click to teleport to chunk")));
                }
                bc.append(message);
            }
        }
        return bc;
    }
}
