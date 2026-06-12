package com.neomechanical.neoperformance.config;

import lombok.Data;

import java.util.Map;

@Data
public class ChatModerationAction {
    private final ChatModerationActionType type;
    private final String command;
    private final String role;
    private final int durationSeconds;
    private final String reason;

    public ChatModerationAction(Map<?, ?> raw) {
        this.type = ChatModerationActionType.fromString(stringValue(raw.get("type")));
        this.command = stringValue(raw.get("command"));
        this.role = stringValue(raw.get("role"));
        this.durationSeconds = intValue(raw.get("durationSeconds"), 300);
        this.reason = stringValueOrDefault(raw.get("reason"), "Inappropriate chat message");
    }

    private static String stringValue(Object value) {
        return value == null ? "" : String.valueOf(value).trim();
    }

    private static String stringValueOrDefault(Object value, String fallback) {
        String text = stringValue(value);
        return text.isEmpty() ? fallback : text;
    }

    private static int intValue(Object value, int fallback) {
        if (value == null) {
            return fallback;
        }

        try {
            return Integer.parseInt(String.valueOf(value).trim());
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }
}
