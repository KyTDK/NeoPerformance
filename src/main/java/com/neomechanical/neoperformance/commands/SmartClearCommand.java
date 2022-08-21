package com.neomechanical.neoperformance.commands;

import com.google.common.collect.ImmutableMap;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartClear.SmartScan;
import com.neomechanical.neoutils.commands.Command;
import com.neomechanical.neoutils.messages.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.function.BiConsumer;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;

public class SmartClearCommand extends Command {

    private final NeoPerformance plugin;

    public SmartClearCommand(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    public static final HashMap<CommandSender, List<Entity>> toBeConfirmed = new HashMap<>();

    public String getName() {
        return "smartclear";
    }

    public String getDescription() {
        return "Clears excess entities from the server";
    }

    public String getSyntax() {
        return "/np smartclear";
    }

    public String getPermission() {
        return "neoperformance.smartclear";
    }

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
                MessageUtil.sendMM(accumulator.player, getLanguageManager().getString("commandGeneric.errorInvalidSyntax", null));
                return;
            }
            accumulator.size(Integer.parseInt(arguments.get(0)));
        }
    }


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
        private final List<World> world = new ArrayList<>();
        private boolean all = false;
        private int clusterSize;
        private boolean cancel = false;
        private boolean force = false;
        private SmartClearAccumulator(CommandSender player) {
            this.player = player;
        }

        public void force() {
            force = true;
        }

        public void size(int clusterSize) {
            this.clusterSize = clusterSize;
        }

        public void world(String name) {
            if (Bukkit.getWorld(name) == null) {
                MessageUtil.sendMM(player, getLanguageManager().getString("commandGeneric.errorWorldNotFound", null));
            }
            world.add(Bukkit.getWorld(name));
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
            //For force
            if (force && !toBeConfirmed.containsKey(player)) {
                force = false;
                SmartScan.clusterLogic(clusterSize, world, plugin.getDataManager().getCommandData(), player, all);
            }
            if (toBeConfirmed.containsKey(player)) {
                //Remove
                for (Entity e : toBeConfirmed.get(player)) {
                    e.remove();
                }
                MessageUtil.sendMM(player, getLanguageManager().getString("smartClear.cleared", null));
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
            SmartScan.clusterLogic(clusterSize, world, plugin.getDataManager().getCommandData(), player, all);
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
