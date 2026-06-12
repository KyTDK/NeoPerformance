package com.neomechanical.neoperformance.moderation;

import com.neomechanical.neoperformance.config.ChatModerationSettings;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Runs remote moderation off the chat event thread (bounded pool), applies a circuit breaker, and
 * returns whether the message should be blocked.
 */
public final class ChatModerationCoordinator implements AutoCloseable {

    private static final int POOL_SIZE = 2;
    private static final long MAX_WAIT_MS = 2500L;

    private final AtomicInteger workerSeq = new AtomicInteger();
    private final ModerationCircuitBreaker circuit = new ModerationCircuitBreaker();
    private final ChatModerationApiClient apiClient = new ChatModerationApiClient();
    private final ExecutorService workers = Executors.newFixedThreadPool(POOL_SIZE, r -> {
        Thread t = new Thread(r, "NeoPerformance-chat-moderation-" + workerSeq.incrementAndGet());
        t.setDaemon(true);
        return t;
    });

    public void resetCircuit() {
        circuit.reset();
    }

    @Override
    public void close() {
        workers.shutdownNow();
    }

    /**
     * @return {@code true} if the message should be removed and actions run.
     */
    public boolean isMessageFlagged(Player player, String message, ChatModerationSettings settings) {
        if (!circuit.isRemoteCallAllowed()) {
            return false;
        }

        long waitMs = Math.min(MAX_WAIT_MS, (long) settings.getApi().getConnectTimeoutMs() + settings.getApi().getReadTimeoutMs() + 400L);

        Future<ModerationApiResult> future = workers.submit(() -> apiClient.moderate(
                player.getName(),
                player.getUniqueId().toString(),
                message,
                settings.getApi(),
                settings.getCategories()
        ));

        try {
            ModerationApiResult result = future.get(Math.max(1L, waitMs), TimeUnit.MILLISECONDS);
            circuit.record(result);
            return result.isFlagged();
        } catch (TimeoutException e) {
            future.cancel(true);
            circuit.record(ModerationApiResult.transientTransport());
            return false;
        } catch (InterruptedException e) {
            future.cancel(true);
            Thread.currentThread().interrupt();
            circuit.record(ModerationApiResult.transientTransport());
            return false;
        } catch (ExecutionException e) {
            circuit.record(ModerationApiResult.transientTransport());
            return false;
        }
    }
}
