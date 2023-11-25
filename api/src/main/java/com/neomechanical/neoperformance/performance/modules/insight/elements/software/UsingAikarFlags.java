package com.neomechanical.neoperformance.performance.modules.insight.elements.software;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.audience.Audience;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.inventory.Book;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.Component;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.modules.insight.elements.InsightElement;
import org.bukkit.entity.Player;

import java.lang.management.ManagementFactory;
import java.util.List;

public class UsingAikarFlags extends InsightElement<Boolean> {
    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        return !currentValue();
    }

    @Override
    public void setDefaultValue() {
        canEditValue = false;
        recommendedValue = true;
        sendDoneMessage = false;
        isAutomatic = false;
    }

    @Override
    public Boolean currentValue() {
        List<String> startupFlags = ManagementFactory.getRuntimeMXBean().getInputArguments();
        return startupFlags.contains("-Dusing.aikars.flags=https://mcflags.emc.gs");
    }

    @Override
    protected void fixInternally(Player player) {
        Audience audience = NeoUtils.getNeoUtilities().getAdventure().sender(player);
        Component bookTitle = Component.text("Insights");
        Component bookAuthor = Component.text("KyTDK");
        Component component = MessageUtil.parseComponent(NeoPerformance.getLanguageManager().getString("insights.aikarFlags", null));
        Book myBook = Book.book(bookTitle, bookAuthor, component);
        audience.openBook(myBook);
    }
}
