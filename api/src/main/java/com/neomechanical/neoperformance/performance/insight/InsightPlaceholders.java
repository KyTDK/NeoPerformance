package com.neomechanical.neoperformance.performance.insight;

import com.neomechanical.neoconfig.neoutils.languages.LanguageManager;
import org.bukkit.Bukkit;

public class InsightPlaceholders {
    private final LanguageManager languageManager;

    public InsightPlaceholders(LanguageManager languageManager) {
        this.languageManager = languageManager;
    }

    public void addPlaceholders() {
        languageManager.addInternalPlaceholder("%SERVERRENDERDISTANCE%", player -> String.valueOf(Bukkit.getViewDistance()));
    }
}
