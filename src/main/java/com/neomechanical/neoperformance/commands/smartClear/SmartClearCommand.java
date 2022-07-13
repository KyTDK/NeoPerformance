package com.neomechanical.neoperformance.commands.smartClear;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.commands.SubCommand;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.smartClear.SmartScan;
import com.neomechanical.neoperformance.utils.Logger;
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
import java.util.Arrays;
import java.util.List;

public class SmartClearCommand extends SubCommand implements PerformanceConfigurationSettings {

    private final NeoPerformance plugin = NeoPerformance.getInstance();
    private final List<String> toBeConfirmed = new ArrayList<>();

    //TODO figure out a better way to do this
    ArrayList<String> specialArgs = new ArrayList<>(
            Arrays.asList("-all", "-force"));

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
        boolean clearAll = false;
        World world = null;
        if (args.length == 2) {
            world = Bukkit.getWorld(args[1]);
            if (world == null) {
                MessageUtil.sendMM(player, plugin.getLanguageManager().getString("commandGeneric.errorWorldNotFound", null));
                return;
            }
        }
        for (String arg : args) {
            switch (arg) {
                case "-all":
                    clearAll = true;
                case "-force":
                    //This arg automatically confirms cluster deletion
                    toBeConfirmed.add(playerAsPlayer.getName());
                case "-world":

            }
        }
        List<List<Entity>> entities;
        if (world == null) {
            entities = SmartScan.scan(10, getCommandData());

        } else {
            //Scan for individual world
            entities = SmartScan.scan(10, getCommandData(), world);
        }
        //One removes largest cluster only
        int toClear = 1;
        if (clearAll) {
            //Requested to remove all clusters
            toClear = entities.size();
        }
        for (int i = 0; i < toClear; i++) {
            //No clusters, show error message and return
            if (entities.isEmpty()) {
                MessageUtil.sendMM(player, plugin.getLanguageManager().getString("smartClear.noEntities", null));
                return;
            }
            //Get cluster
            List<Entity> entityList = entities.get(i);
            NamedTextColor color;
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
                return;
            }
            //Command to review cluster
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
                return;
            }
        }
    }

    @Override
    public List<String> tabSuggestions() {
        return specialArgs;
    }

    private boolean isConfirmed(String playerName) {
        if (toBeConfirmed.remove(playerName)) {
            return true;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!toBeConfirmed.remove(playerName)) {
                    Logger.warn("An error occured for SmartClear");
                }
            }
        }.runTaskLater(NeoPerformance.getInstance(), 20L * 10);
        toBeConfirmed.add(playerName);
        return false;
    }
}
