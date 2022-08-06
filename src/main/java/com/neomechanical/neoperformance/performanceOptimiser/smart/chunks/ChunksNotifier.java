package com.neomechanical.neoperformance.performanceOptimiser.smart.chunks;

import com.neomechanical.neoutils.messages.MessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class ChunksNotifier {
    public static void sendChatData(List<Chunk> chunks, World world, Player playerAsPlayer) {
        TextComponent.Builder bc = getChatData(chunks, world, playerAsPlayer);
        MessageUtil.sendMM(playerAsPlayer, bc.build());
    }

    public static TextComponent.Builder getChatData(List<Chunk> chunks, World world, Player playerAsPlayer) {
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
            Entity entityToLocate = chunk.getEntities()[0];
            Location location = new Location(world, Math.round(entityToLocate.getLocation().getX()), Math.round(entityToLocate.getLocation().getY()),
                    Math.round(entityToLocate.getLocation().getZ()));
            bc.append(Component.text("  Chunk: ").color(color).decorate(TextDecoration.BOLD))
                    .append(Component.text(location.getX() + " " + location.getZ()).color(color).decorate(TextDecoration.BOLD))
                    .append(Component.text(" - Entities: ").color(color).decorate(TextDecoration.BOLD))
                    .append(Component.text(String.valueOf(chunk.getEntities().length)).color(color).decorate(TextDecoration.BOLD));
            if (world == null) {
                bc.append(Component.text(" - World: ").color(color).decorate(TextDecoration.BOLD));
                bc.append(Component.text(chunk.getWorld().getName()).color(color).decorate(TextDecoration.BOLD));
            }
            bc.color(color).decorate(TextDecoration.BOLD);
            //Append new line
            if (chunks.indexOf(chunk) != chunks.size() - 1) {
                bc.append(Component.newline());
            }
            bc.clickEvent(ClickEvent.runCommand(
                    "/minecraft:execute in " + chunk.getWorld().getKey()
                            + " run tp " + playerAsPlayer.getName() + " " + location.getX()
                            + " " + location.getY() + " " + location.getZ()));
            bc.hoverEvent(HoverEvent.showText(Component.text("Click to teleport to chunk")));
        }
        return bc;
    }
}
