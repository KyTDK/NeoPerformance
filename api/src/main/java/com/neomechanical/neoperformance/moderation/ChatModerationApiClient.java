package com.neomechanical.neoperformance.moderation;

import com.neomechanical.neoperformance.config.ChatModerationApiSettings;
import com.neomechanical.neoperformance.config.ChatModerationCategorySettings;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Stateless HTTP client for the Neomechanical chat moderation endpoint. Does not apply circuit
 * logic — see {@link ChatModerationCoordinator}.
 */
public final class ChatModerationApiClient {

    private static final HttpClient HTTP = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    public ModerationApiResult moderate(
            String playerName,
            String playerUuid,
            String message,
            ChatModerationApiSettings apiSettings,
            ChatModerationCategorySettings categorySettings
    ) {
        long connectMs = Math.max(1, apiSettings.getConnectTimeoutMs());
        long readMs = Math.max(1, apiSettings.getReadTimeoutMs());
        long totalMs = Math.min(60_000L, connectMs + readMs + 500L);

        String body = createPayload(playerName, playerUuid, message, categorySettings);
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder(URI.create(apiSettings.getEndpoint()))
                    .timeout(Duration.ofMillis(totalMs))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiSettings.getApiKey())
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .build();
        } catch (IllegalArgumentException e) {
            return ModerationApiResult.transientTransport();
        }

        try {
            HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            int status = response.statusCode();
            if (status == 401 || status == 403) {
                return ModerationApiResult.clientAuth();
            }
            if (status == 429 || status >= 500) {
                return ModerationApiResult.transientTransport();
            }
            if (status < 200 || status >= 300) {
                return ModerationApiResult.clear();
            }
            return matchesPositiveSignal(response.body(), categorySettings)
                    ? ModerationApiResult.flagged()
                    : ModerationApiResult.clear();
        } catch (IOException e) {
            return ModerationApiResult.transientTransport();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ModerationApiResult.transientTransport();
        }
    }

    private String createPayload(String playerName, String playerUuid, String message, ChatModerationCategorySettings categorySettings) {
        List<String> categories = new ArrayList<>();
        if (categorySettings.isSexual()) {
            categories.add("\"sexual\"");
        }
        if (categorySettings.isHate()) {
            categories.add("\"hate\"");
        }

        return "{"
                + "\"player\":\"" + escape(playerName) + "\","
                + "\"uuid\":\"" + escape(playerUuid) + "\","
                + "\"message\":\"" + escape(message) + "\","
                + "\"categories\":[" + String.join(",", categories) + "]"
                + "}";
    }

    private boolean matchesPositiveSignal(String responseBody, ChatModerationCategorySettings categorySettings) {
        if (Pattern.compile("\"flagged\"\\s*:\\s*true", Pattern.CASE_INSENSITIVE).matcher(responseBody).find()) {
            return true;
        }

        if (categorySettings.isSexual() && Pattern.compile("\"sexual\"\\s*:\\s*true", Pattern.CASE_INSENSITIVE).matcher(responseBody).find()) {
            return true;
        }

        return categorySettings.isHate() && Pattern.compile("\"hate\"\\s*:\\s*true", Pattern.CASE_INSENSITIVE).matcher(responseBody).find();
    }

    private static String escape(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}
