package com.neomechanical.neoperformance.performance.insight;

import com.neomechanical.neoconfig.neoutils.inventory.InventoryUtil;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryGUI;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryItem;
import com.neomechanical.neoconfig.neoutils.items.ItemUtil;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class InsightGUIReport {
    private final InventoryGUI inventoryGUI = InventoryUtil.createInventoryGUI(null, 54, "Insights");

    public InsightGUIReport buildReport(HashMap<String, HashMap<String, InsightElement>> insights) {
        insights.forEach((category, categoryInsights) -> {
            categoryInsights.forEach((insightName, insightElement) -> {
                if (!insightElement.isInsightApplicableOrAlreadyPresent()) {
                    return;
                }
                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.translateAlternateColorCodes('&', "&aCategory: ") + category);

                Consumer<InventoryClickEvent> fixAction = event -> {
                    insightElement.fix();
                    new Insights().openInsights(event.getWhoClicked());
                    MessageUtil.sendMM(event.getWhoClicked(), NeoPerformance.getLanguageManager().getString("insights.fixAutomaticDone", null));
                };
                InventoryItem item = new InventoryItem.InventoryItemBuilder(
                        () -> ItemUtil.createItem(Material.ANVIL, 1, "&7" + insightName + " (Click to fix)", lore))
                        .setAction(fixAction)
                        .build();
                inventoryGUI.addItem(item);
            });
        });
        return this;
    }

    public void openReport(CommandSender commandSender) {
        inventoryGUI.open((Player) commandSender);
    }
}
