package com.neomechanical.neoperformance.performance.insight;

import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.Component;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.TextComponent;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.event.ClickEvent;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.event.HoverEvent;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.utils.messages.Messages;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class InsightReport {
    private final TextComponent.Builder builder = Component.text();

    public InsightReport buildReport(HashMap<String, HashMap<String, InsightElement>> insights) {
        insights.forEach((category, categoryInsights) -> {
            TextComponent.Builder insightElements = Component.text();
            categoryInsights.forEach((insightName, insightElement) -> {
                insightElements
                        .append(Component.newline())
                        .append(Component.text("  • "))
                        .append(MessageUtil.parseComponent(insightName + ": <red>" + insightElement.currentValue() + "<white> | "
                                + NeoPerformance.getLanguageManager().getString("insights.recommended", null)
                                + ": <green>" + insightElement.recommendedValue()))
                        .append(MessageUtil.parseComponent(" <white><bold>(click to fix)"))
                        .clickEvent(ClickEvent.runCommand("/np insights fix " + category.replace(" ", "-") + " " + insightName))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to fix")));
            });
            if (!insightElements.children().isEmpty()) {
                if (!builder.children().isEmpty()) {
                    builder.append(Component.newline());
                }
                builder.append(MessageUtil.parseComponent("<gray><bold>" + category + ": "))
                        .append(insightElements);
            }
        });
        if (builder.children().isEmpty()) {
            builder.append(MessageUtil.parseComponent(NeoPerformance.getLanguageManager().getString("insights.noElements", null)));
        }
        return this;
    }

    public void sendReport(CommandSender commandSender) {
        TextComponent message = builder.build();
        MessageUtil.send(commandSender, Messages.MAIN_INSIGHTS_PREFIX);
        MessageUtil.sendMM(commandSender, message);
        MessageUtil.send(commandSender, Messages.MAIN_SUFFIX);
    }
}
