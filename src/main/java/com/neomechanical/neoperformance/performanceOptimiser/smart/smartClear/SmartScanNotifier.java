package com.neomechanical.neoperformance.performanceOptimiser.smart.smartClear;

import com.neomechanical.neoperformance.utils.messages.MessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class SmartScanNotifier {
    public static void sendChatData(CommandSender player, int toClear, List<Entity> entityList) {
        TextComponent.Builder builder = getChatData(player, toClear, entityList);
        MessageUtil.sendMM(player, builder.build());
    }

    public static TextComponent.Builder getChatData(CommandSender player, int toClear, List<Entity> entityList) {
        final TextComponent.Builder builder = Component.text();
        NamedTextColor color;
        for (int i = 0; i < toClear; i++) {
            //Calculate colour to represent severity of cluster
            if (entityList.size() > 100) {
                color = NamedTextColor.RED;
            } else if (entityList.size() > 50) {
                color = NamedTextColor.YELLOW;
            } else {
                color = NamedTextColor.GREEN;
            }
            //Get first entity to use for location
            Entity entity = entityList.get(0);
            Location location = entity.getLocation();
            if (location.getWorld() == null) {
                continue;
            }
            //Command to review cluster
            String command = "/minecraft:execute in " + location.getWorld().getKey()
                    + " run tp " + player.getName() + " " + location.getX()
                    + " " + location.getY() + " " + location.getZ();
            builder.append(Component.text("Found cluster of entities with size " + entityList.size())).color(color);
            if (player instanceof Player) {
                builder.append(Component.text(" - Click to teleport"))
                        .clickEvent(
                                ClickEvent.runCommand(command))
                        .hoverEvent(
                                HoverEvent.showText(Component.text("Click to teleport")
                                )
                        );
            } else {
                builder.append(Component.text(" - Location: " + location.getX() + "," + location.getY() + "," + location.getZ()));
            }
        }
        return builder;
    }
}
