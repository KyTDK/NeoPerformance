package com.neomechanical.neoperformance.performance.insight;

import com.neomechanical.neoconfig.anvilgui.AnvilGUI;
import com.neomechanical.neoconfig.neoutils.inventory.InventoryUtil;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryGUI;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryItem;
import com.neomechanical.neoconfig.neoutils.items.ItemUtil;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.insight.elements.InsightElement;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Consumer;

public class InsightGUIReport {
    private final InventoryGUI inventoryGUI = InventoryUtil.createInventoryGUI(null, 54, "Insights");

    public InsightGUIReport buildReport(HashMap<String, HashMap<String, InsightElement<?>>> insights) {
        insights.forEach((category, categoryInsights) -> {
            categoryInsights.forEach((insightName, insightElement) -> {
                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.translateAlternateColorCodes('&', "&7Category: ") + category);
                lore.add(ChatColor.translateAlternateColorCodes('&', "&7Current value: &4") + insightElement.currentValue());
                lore.add(ChatColor.translateAlternateColorCodes('&', "&7Recommended value: &a") + insightElement.getRecommendedValue());
                lore.add(ChatColor.translateAlternateColorCodes('&', "&f&lRight click &r&7to set custom value"));

                Consumer<InventoryClickEvent> fixAction = event -> {
                    insightElement.fix();
                    new Insights().openInsights(event.getWhoClicked());
                    MessageUtil.sendMM(event.getWhoClicked(), NeoPerformance.getLanguageManager().getString("insights.fixAutomaticDone", null));
                };

                Consumer<InventoryClickEvent> changeRecommendedValue = event -> {
                    new AnvilGUI.Builder().
                            onClick((slot, snapshot) -> {
                                insightElement.setRecommendedValue(snapshot.getText());
                                insightElement.fix();
                                new Insights().openInsights(event.getWhoClicked());
                                return Collections.singletonList(AnvilGUI.ResponseAction.close());
                            })
                            .onClose(stateSnapshot -> {
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        new Insights().openInsights(event.getWhoClicked());
                                    }
                                }.runTaskLater(NeoPerformance.getInstance(), 1L);
                            })
                            .text(insightElement.getRecommendedValue())
                            .title("Change recommended value")
                            .plugin(NeoPerformance.getInstance())
                            .open((Player) event.getWhoClicked());
                };

                InventoryItem item = new InventoryItem.InventoryItemBuilder(
                        () -> ItemUtil.createItem(Material.ANVIL, 1, "&7" + insightName.replace("-", " ") + " &f&l(Left click to fix)", lore))
                        .setAction(fixAction, ClickType.LEFT)
                        .setAction(changeRecommendedValue, ClickType.RIGHT)
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
