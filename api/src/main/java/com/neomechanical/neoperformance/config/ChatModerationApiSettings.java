package com.neomechanical.neoperformance.config;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;

@Data
public class ChatModerationApiSettings {
    private final String endpoint;
    private final String apiKey;
    private final int connectTimeoutMs;
    private final int readTimeoutMs;

    public ChatModerationApiSettings(FileConfiguration fileConfiguration) {
        this.endpoint = fileConfiguration.getString("chat_moderation.api.endpoint", "https://api.neomechanical.com/v1/moderation/chat");
        this.apiKey = fileConfiguration.getString("chat_moderation.api.apiKey", "");
        this.connectTimeoutMs = fileConfiguration.getInt("chat_moderation.api.connectTimeoutMs", 3000);
        this.readTimeoutMs = fileConfiguration.getInt("chat_moderation.api.readTimeoutMs", 3000);
    }
}
