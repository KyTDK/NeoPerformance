package com.neomechanical.neoperformance.config;

public enum ChatModerationActionType {
    CLEAR_CHAT,
    MUTE,
    KICK,
    BAN,
    TIMEOUT,
    GIVE_ROLE,
    TAKE_ROLE,
    TEMP_ROLE,
    COMMAND;

    public static ChatModerationActionType fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return COMMAND;
        }

        try {
            return ChatModerationActionType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return COMMAND;
        }
    }
}
