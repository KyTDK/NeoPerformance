package com.neomechanical.neoperformance.performance.smart.smartReport;

import com.neomechanical.neoconfig.neoutils.languages.LanguageManager;
import com.neomechanical.neoperformance.performance.smart.smartReport.utils.CPU;
import com.neomechanical.neoperformance.performance.smart.smartReport.utils.Grading;
import com.neomechanical.neoperformance.performance.smart.smartReport.utils.Memory;
import org.bukkit.entity.Player;

public class SmartReportPlaceholders {
    private final LanguageManager languageManager;

    public SmartReportPlaceholders(LanguageManager languageManager) {
        this.languageManager=languageManager;
    }
    public void addPlaceholders() {
        languageManager
                //Add memory placeholders
                .addInternalPlaceholder("%USEDMEMORY%", (Player player) -> String.valueOf(Memory.usedMemory()))
                .addInternalPlaceholder("%FREEMEMORY%", (Player player) -> String.valueOf(Memory.freeMemory()))
                .addInternalPlaceholder("%MAXMEMORY%", (Player player) -> String.valueOf(Memory.maxMemory()))
                //Add cpu placeholders
                .addInternalPlaceholder("%AVAILABLEPROCESSORS%", (Player player) -> String.valueOf(CPU.availableProcessors()))
                .addInternalPlaceholder("%CPUPROCESSLOAD%", (Player player) -> String.valueOf(CPU.getProcessCpuLoad()))
                .addInternalPlaceholder("%CPUSYSTEMLOAD%", (Player player) -> String.valueOf(CPU.getSystemCpuLoad()));
    }
}
