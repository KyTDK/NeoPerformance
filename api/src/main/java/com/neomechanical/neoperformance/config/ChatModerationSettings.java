package com.neomechanical.neoperformance.config;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ChatModerationSettings {
    private final boolean enabled;
    private final ChatModerationApiSettings api;
    private final ChatModerationCategorySettings categories;
    private final List<ChatModerationAction> actions;

    public ChatModerationSettings(FileConfiguration fileConfiguration) {
        this.enabled = fileConfiguration.getBoolean("chat_moderation.enabled", false);
        this.api = new ChatModerationApiSettings(fileConfiguration);
        this.categories = new ChatModerationCategorySettings(fileConfiguration);
        this.actions = loadActions(fileConfiguration);
    }

    private List<ChatModerationAction> loadActions(FileConfiguration fileConfiguration) {
        List<ChatModerationAction> loadedActions = new ArrayList<>();
        List<Map<?, ?>> actionMapList = fileConfiguration.getMapList("chat_moderation.actions");

        for (Map<?, ?> rawAction : actionMapList) {
            loadedActions.add(new ChatModerationAction(rawAction));
        }

        return loadedActions;
    }
}
