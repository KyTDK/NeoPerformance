package com.neomechanical.neoperformance.config;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;

@Data
public class ChatModerationCategorySettings {
    private final boolean sexual;
    private final boolean hate;

    public ChatModerationCategorySettings(FileConfiguration fileConfiguration) {
        this.sexual = fileConfiguration.getBoolean("chat_moderation.categories.sexual", true);
        this.hate = fileConfiguration.getBoolean("chat_moderation.categories.hate", true);
    }
}
