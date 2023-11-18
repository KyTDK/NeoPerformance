package com.neomechanical.neoperformance.performance.insight.elements.software;

import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.insight.elements.InsightElement;
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
    }

    @Override
    public Boolean currentValue() {
        List<String> startupFlags = ManagementFactory.getRuntimeMXBean().getInputArguments();
        return startupFlags.contains("-Dusing.aikars.flags=https://mcflags.emc.gs");
    }

    @Override
    protected void fixInternally(Player player) {
        MessageUtil.sendMM(player, NeoPerformance.getLanguageManager().getString("insights.aikarFlags", null));
    }
}
