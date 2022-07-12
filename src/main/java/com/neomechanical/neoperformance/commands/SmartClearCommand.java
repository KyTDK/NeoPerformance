package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.smartClear.SmartScan;
import com.neomechanical.neoperformance.utils.MessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SmartClearCommand extends SubCommand {

    private final NeoPerformance plugin = NeoPerformance.getInstance();
    private final List<String> toBeConfirmed = new ArrayList<>();

    @Override
    public String getName() {
        return "smartclear";
    }

    @Override
    public String getDescription() {
        return "Clears excess entities from the server";
    }

    @Override
    public String getSyntax() {
        return "/np smartclear";
    }

    @Override
    public String getPermission() {
        return "neoperformance.smartclear";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public void perform(CommandSender player, String[] args) {
        Player playerAsPlayer = (Player) player;
        World world = null;
        if (args.length == 2) {
            world = Bukkit.getWorld(args[1]);
            if (world == null) {
                MessageUtil.sendMM(player, plugin.getLanguageManager().getString("commandGeneric.errorWorldNotFound", null));
                return;
            }
        }
        if (world == null) {
            List<List<Entity>> entities = SmartScan.scan(10);
            if (entities.isEmpty()) {
                MessageUtil.sendMM(player, plugin.getLanguageManager().getString("smartClear.noEntities", null));
                return;
            }
            List<Entity> entityList = entities.get(0);
            NamedTextColor color;
            if (entityList.size() > 100) {
                color = NamedTextColor.RED;
            } else if (entityList.size() > 50) {
                color = NamedTextColor.YELLOW;
            } else {
                color = NamedTextColor.GREEN;
            }
            Entity entity = entityList.get(0);
            Location location = entity.getLocation();
            if (location.getWorld()==null) {
                return;
            }
            String command = "/minecraft:execute in " + location.getWorld().getKey()
                    + " run tp " + playerAsPlayer.getName() + " " + location.getX()
                    + " " + location.getY() + " " + location.getZ();
            final TextComponent textComponent = Component.text()
                    .content("Found cluster of entities with size " + entityList.size()).color(color)
                    .append(Component.text(" - Click to teleport"))
                    .clickEvent(
                            ClickEvent.runCommand(command))
                    .hoverEvent(
                            HoverEvent.showText(Component.text("Click to teleport")
                            )
                    )
                    .build();
            if (isConfirmed(playerAsPlayer.getName())) {
                //Remove
                for (Entity e : entityList) {
                    if (e instanceof Player) {
                        continue;
                    }
                    e.remove();
                }
                MessageUtil.sendMM(player, plugin.getLanguageManager().getString("smartClear.cleared", null));
            } else {
                MessageUtil.sendMM(player, textComponent);
                MessageUtil.sendMM(player, plugin.getLanguageManager().getString("smartClear.confirm", null));
                toBeConfirmed.add(playerAsPlayer.getName());
            }
        } else {
            SmartScan.scan(10, world);
        }
    }

    @Override
    public List<String> tabSuggestions() {
        return null;
    }

    private boolean isConfirmed(String playerName) {
        if (toBeConfirmed.remove(playerName)) {
            return true;
        }
        toBeConfirmed.add(playerName);
        new BukkitRunnable() {
            @Override
            public void run() {
                SmartClearCommand.this.toBeConfirmed.remove(playerName);
                Bukkit.broadcastMessage("" + SmartClearCommand.this.toBeConfirmed);
            }
        }.runTaskLater(NeoPerformance.getInstance(), 20L * 10);
        return false;
    }
}
