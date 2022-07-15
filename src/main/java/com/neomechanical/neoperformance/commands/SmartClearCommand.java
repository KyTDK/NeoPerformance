package com.neomechanical.neoperformance.commands;

import com.google.common.collect.ImmutableMap;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
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

import java.util.*;
import java.util.function.BiConsumer;

public class SmartClearCommand extends SubCommand implements PerformanceConfigurationSettings {

    private static final NeoPerformance plugin = NeoPerformance.getInstance();
    private static final HashMap<CommandSender, List<Entity>> toBeConfirmed = new HashMap<>();

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

    private static final Map<String, BiConsumer<SmartClearAccumulator, List<String>>> PROCESSORS =
            new ImmutableMap.Builder<String, BiConsumer<SmartClearAccumulator, List<String>>>()
                    .put("-force", SmartClearCommand::force)
                    .put("-world", SmartClearCommand::world)
                    .put("-all", (accumulator, accumulator2) -> all(accumulator))
                    .put("-size", SmartClearCommand::size)
                    .put("-cancel", SmartClearCommand::cancel)
                    .build();

    private static void force(SmartClearAccumulator accumulator, List<String> arguments) {
        accumulator.force();
    }

    private static void world(SmartClearAccumulator accumulator, List<String> arguments) {
        accumulator.world(String.join(" ", arguments));
    }

    private static void all(SmartClearAccumulator accumulator) {
        accumulator.all();
    }

    private static void cancel(SmartClearAccumulator accumulator, List<String> arguments) {
        accumulator.cancel();
    }

    private static void size(SmartClearAccumulator accumulator, List<String> arguments) {
        if (arguments.size() == 1) {
            if (Integer.getInteger(arguments.get(0)) != null) {
                MessageUtil.sendMM(accumulator.player, plugin.getLanguageManager().getString("commandGeneric.errorInvalidSyntax", null));
                return;
            }
            accumulator.size(Integer.parseInt(arguments.get(0)));
        }
    }


    @Override
    public void perform(CommandSender player, String[] args) {
        Stack<String> commandStack = new Stack<>();
        commandStack.addAll(Arrays.asList(args));
        List<String> commandArgs = new ArrayList<>();
        SmartClearAccumulator accumulator = new SmartClearAccumulator(player);
        while (!commandStack.isEmpty()) {
            String element = commandStack.pop();
            if (PROCESSORS.containsKey(element)) {
                PROCESSORS.get(element).andThen((acc, list) -> list.clear()).accept(accumulator, commandArgs);
            } else {
                commandArgs.add(element);
            }
        }
        accumulator.complete();
    }

    class SmartClearAccumulator {
        private final CommandSender player;
        private World world;
        private boolean all = false;
        private int clusterSize;
        private boolean cancel = false;

        private SmartClearAccumulator(CommandSender player) {
            this.player = player;
        }

        public void force() {
            if (!toBeConfirmed.containsKey(player)) {
                clusterLogic();
            }
        }

        public void size(int clusterSize) {
            this.clusterSize = clusterSize;
        }

        public void world(String name) {
            world = Bukkit.getWorld(name);
            if (world == null) {
                MessageUtil.sendMM(player, plugin.getLanguageManager().getString("commandGeneric.errorWorldNotFound", null));
            }
        }

        public void cancel() {
            toBeConfirmed.remove(player);
            cancel = true;
        }

        public void all() {
            all = true;
        }

        public void complete() {
            if (cancel) {
                MessageUtil.sendMM(player, "<red><bold>Cancelled");
                cancel = false;
                return;
            }
            if (toBeConfirmed.containsKey(player)) {
                //Remove
                for (Entity e : toBeConfirmed.get(player)) {
                    if (e instanceof Player) {
                        continue;
                    }
                    e.remove();
                }
                MessageUtil.sendMM(player, plugin.getLanguageManager().getString("smartClear.cleared", null));
                toBeConfirmed.remove(player);
                return;
            }
            //Remove from list if not confirmed after 10 seconds
            new BukkitRunnable() {
                @Override
                public void run() {
                    toBeConfirmed.remove(player);
                }
            }.runTaskLater(plugin, 20L * 10);
            clusterLogic();
        }

        private void clusterLogic() {
            //Message logic and construct entity list
            NamedTextColor color;
            List<List<Entity>> clusters;
            if (world == null) {
                //Scan for all worlds
                World[] worlds = Bukkit.getWorlds().toArray(World[]::new);
                clusters = SmartScan.scan(10, clusterSize, getCommandData(), worlds);

            } else {
                //Scan for individual world
                clusters = SmartScan.scan(10, clusterSize, getCommandData(), world);
            }
            //One removes the largest cluster only
            int toClear = 1;
            if (all) {
                toClear = clusters.size();
            }
            //No clusters, show error message and return
            if (clusters.isEmpty()) {
                MessageUtil.sendMM(player, plugin.getLanguageManager().getString("smartClear.noEntities", null));
                return;
            }
            for (int i = 0; i < toClear; i++) {
                //Get cluster
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
                String command = "/minecraft:execute in " + location.getWorld().getKey()
                        + " run tp " + player.getName() + " " + location.getX()
                        + " " + location.getY() + " " + location.getZ();
                final TextComponent.Builder builder = Component.text()
                        .content("Found cluster of entities with size " + entityList.size()).color(color);
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
                MessageUtil.sendMM(player, builder.build());
                if (toBeConfirmed.containsKey(player)) {
                    toBeConfirmed.get(player).addAll(entityList);
                } else {
                    toBeConfirmed.put(player, entityList);
                }
            }
            MessageUtil.sendMM(player, plugin.getLanguageManager().getString("smartClear.confirm", null));
        }
    }

    @Override
    public List<String> tabSuggestions() {
        return PROCESSORS.keySet().stream().toList();
    }

    @Override
    public Map<String, List<String>> mapSuggestions() {
        Map<String, List<String>> suggestions = new HashMap<>();
        List<String> worldNames = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) {
            worldNames.add(world.getName());
        }
        //must have space in the map, otherwise it will be suggested it before you press space.
        suggestions.put("-world", worldNames);
        return suggestions;
    }
}
