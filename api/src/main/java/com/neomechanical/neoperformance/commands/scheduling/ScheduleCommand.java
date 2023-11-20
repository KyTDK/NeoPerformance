package com.neomechanical.neoperformance.commands.scheduling;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoconfig.neoutils.inventory.InventoryUtil;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryGUI;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryItem;
import com.neomechanical.neoconfig.neoutils.items.ItemUtil;
import com.neomechanical.neoperformance.performance.modules.smartSchedule.guis.IntervalSchedule;
import com.neomechanical.neoperformance.performance.modules.smartSchedule.guis.TimeDateSchedule;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class ScheduleCommand extends Command {
    @Override
    public String getName() {
        return "schedule";
    }

    @Override
    public String getDescription() {
        return "Schedule tasks to run";
    }

    @Override
    public String getSyntax() {
        return "/np schedule";
    }

    @Override
    public String getPermission() {
        return "neoperformance.schedule";
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        Player player = (Player) commandSender;
        TimeDateSchedule timeDateSchedule = new TimeDateSchedule();
        IntervalSchedule intervalSchedule = new IntervalSchedule();
        InventoryGUI inventoryGUI = InventoryUtil.createInventoryGUI(null, 27, "Scheduling");
        //First layer - Run at a date/time or at intervals
        //For date/time create an anvil gui to set time and date and create buttons to cycle time and date.
        //For intervals create an anvil gui to set intervals, such as second, minute, hour, day and cycle buttons
        inventoryGUI.setItem(3, new InventoryItem.InventoryItemBuilder(() ->
                ItemUtil.createItem(Material.WATCH, "Date/Time"))
                    .setAction((inventoryClickEvent) -> timeDateSchedule.getInventory()
                        .open((Player) inventoryClickEvent.getWhoClicked()))
                .build());
        inventoryGUI.setItem(5, new InventoryItem.InventoryItemBuilder(() ->
                ItemUtil.createItem(Material.COMPASS, "Interval"))
                .setAction((inventoryClickEvent) -> intervalSchedule.getInventory()
                        .open((Player) inventoryClickEvent.getWhoClicked()))
                .build());
        inventoryGUI.open(player);

    }

    @Override
    public List<String> tabSuggestions() {
        return null;
    }

    @Override
    public Map<String, List<String>> mapSuggestions() {
        return null;
    }
}
