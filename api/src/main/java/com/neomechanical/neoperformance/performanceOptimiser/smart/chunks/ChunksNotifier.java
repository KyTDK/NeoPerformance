package com.neomechanical.neoperformance.performanceOptimiser.smart.chunks;

import com.neomechanical.kyori.adventure.text.Component;
import com.neomechanical.kyori.adventure.text.TextComponent;
import com.neomechanical.kyori.adventure.text.event.ClickEvent;
import com.neomechanical.kyori.adventure.text.event.HoverEvent;
import com.neomechanical.kyori.adventure.text.format.NamedTextColor;
import com.neomechanical.kyori.adventure.text.format.TextDecoration;
import com.neomechanical.neoperformance.utils.messages.Messages;
import com.neomechanical.neoutils.NeoUtils;
import com.neomechanical.neoutils.messages.MessageUtil;
import com.neomechanical.neoutils.version.worlds.IWorldNMS;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class ChunksNotifier {
    public static void sendChatData(List<Chunk> chunks, World world, Player playerAsPlayer) {
        TextComponent.Builder bc = getChatData(chunks, world, playerAsPlayer);
        MessageUtil messageUtil = new MessageUtil();
        messageUtil.addComponent(bc.build());
        messageUtil.sendNeoComponentMessage(playerAsPlayer, Messages.MAIN_PREFIX, Messages.MAIN_SUFFIX);
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
            TextComponent.Builder message = Component.text();
            Entity entityToLocate = chunk.getEntities()[0];
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
            IWorldNMS worldNMS = (IWorldNMS) NeoUtils.getInternalVersions().get("worlds");
            message.clickEvent(ClickEvent.runCommand(
                    "/minecraft:execute in " + worldNMS.getWorldNamespaceKey(chunk.getWorld())
                            + " run tp " + playerAsPlayer.getName() + " " + location.getX()
                            + " " + location.getY() + " " + location.getZ()));
            message.hoverEvent(HoverEvent.showText(Component.text("Click to teleport to chunk")));
            bc.append(message);
        }
        return bc;
    }
}
