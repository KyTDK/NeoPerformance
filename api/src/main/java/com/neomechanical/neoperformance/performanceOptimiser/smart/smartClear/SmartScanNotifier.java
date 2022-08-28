package com.neomechanical.neoperformance.performanceOptimiser.smart.smartClear;

import com.neomechanical.kyori.adventure.text.Component;
import com.neomechanical.kyori.adventure.text.TextComponent;
import com.neomechanical.kyori.adventure.text.event.ClickEvent;
import com.neomechanical.kyori.adventure.text.event.HoverEvent;
import com.neomechanical.kyori.adventure.text.format.NamedTextColor;
import com.neomechanical.neoperformance.utils.messages.Messages;
import com.neomechanical.neoutils.NeoUtils;
import com.neomechanical.neoutils.messages.MessageUtil;
import com.neomechanical.neoutils.version.worlds.IWorldNMS;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;

public class SmartScanNotifier {
    public static void sendChatData(CommandSender player, int toClear, List<List<Entity>> clusters) {
        TextComponent.Builder builder = getChatData(player, toClear, clusters);
        if (builder.children().isEmpty()) {
            MessageUtil.sendMM(player, getLanguageManager().getString("smartClear.noEntities", null));
            return;
        }
        MessageUtil messageUtil = new MessageUtil();
        messageUtil.addComponent(builder.build());
        messageUtil.sendNeoComponentMessage(player, Messages.MAIN_PREFIX, Messages.MAIN_SUFFIX);
    }

    public static TextComponent.Builder getChatData(CommandSender player, int toClear, List<List<Entity>> clusters) {
        final TextComponent.Builder builder = Component.text();
        NamedTextColor color;
        if (clusters.isEmpty()) {
            return builder;
        }
        for (int i = 0; i < toClear; i++) {
            List<Entity> entityList = clusters.get(i);
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
            IWorldNMS worldNMS = (IWorldNMS) NeoUtils.getInternalVersions().get("worlds");
            String command = "/minecraft:execute in " + worldNMS.getWorldNamespaceKey(location.getWorld())
                    + " run tp " + player.getName() + " " + location.getX()
                    + " " + location.getY() + " " + location.getZ();
            TextComponent.Builder message = Component.text();
            message.append(Component.text("  Found cluster of entities with size " + entityList.size())).color(color);
            if (player instanceof Player) {
                message.append(Component.text(" - Click to teleport"))
                        .clickEvent(
                                ClickEvent.runCommand(command))
                        .hoverEvent(
                                HoverEvent.showText(Component.text("Click to teleport")
                                )
                        );
            } else {
                message.append(Component.text(" - Location: " + location.getX() + "," + location.getY() + "," + location.getZ()));
            }
            if (i < toClear - 1) {
                message.append(Component.newline());
            }
            builder.append(message);
        }
        return builder;
    }
}
