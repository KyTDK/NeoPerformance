package com.neomechanical.neoperformance.performance.modules.insight;

import com.neomechanical.neoconfig.anvilgui.AnvilGUI;
import com.neomechanical.neoconfig.neoutils.inventory.InventoryUtil;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryGUI;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryItem;
import com.neomechanical.neoconfig.neoutils.items.ItemUtil;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.modules.insight.elements.InsightElement;
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
                if (insightElement.canEditValue) {
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&f&lRight click &r&7to set custom value"));
                }

                Consumer<InventoryClickEvent> fixAction = event -> {
                    insightElement.fix((Player) event.getWhoClicked());
                    if (!insightElement.closeOnFix) {
                        new Insights().openInsights(event.getWhoClicked());
                    }
                    if (insightElement.sendDoneMessage) {
                        MessageUtil.sendMM(event.getWhoClicked(), NeoPerformance.getLanguageManager().getString("insights.fixAutomaticDone", null));
                    }
                };

                Consumer<InventoryClickEvent> changeRecommendedValue = event -> {
                    new AnvilGUI.Builder().
                            onClick((slot, snapshot) -> {
                                insightElement.setRecommendedValue(snapshot.getText());
                                insightElement.fix((Player) event.getWhoClicked());
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


                InventoryItem.InventoryItemBuilder item = new InventoryItem.InventoryItemBuilder(
                        () -> {
                            String elementItemName = "&7" + insightName.replace("-", " ");
                            if (insightElement.canFix) {
                                elementItemName = elementItemName + " &f&l(Left click to fix)";
                            }
                            return ItemUtil.createItem(Material.ANVIL, 1, elementItemName, lore);
                        });
                if (insightElement.canFix) {
                    item.setAction(fixAction, ClickType.LEFT);
                }
                if (insightElement.canEditValue) {
                    item.setAction(changeRecommendedValue, ClickType.RIGHT);
                }
                inventoryGUI.addItem(item.build());
            });
        });
        return this;
    }

    public void openReport(CommandSender commandSender) {
        inventoryGUI.open((Player) commandSender);
    }
}
