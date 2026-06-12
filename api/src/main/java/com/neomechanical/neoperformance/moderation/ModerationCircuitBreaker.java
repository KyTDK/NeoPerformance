package com.neomechanical.neoperformance.moderation;

import com.neomechanical.neoperformance.utils.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Minimal circuit breaker: after repeated transient remote failures, pauses outbound moderation
 * calls for a cooldown so chat stays responsive. Successful moderation clears the failure streak.
 */
public final class ModerationCircuitBreaker {

    private static final int TRANSIENT_TRIP_THRESHOLD = 3;
    private static final long PAUSE_NANOS = TimeUnit.SECONDS.toNanos(60);

    private final Object lock = new Object();
    private int transientFailures;
    /** 0 = not paused; otherwise nanoTime deadline until remote calls resume. */
    private long pausedUntilNanos;
    private final AtomicBoolean transientPauseLogged = new AtomicBoolean();
    private final AtomicBoolean clientAuthLogged = new AtomicBoolean();

    /**
     * Clears pause and failure state (e.g. on /np reload).
     */
    public void reset() {
        synchronized (lock) {
            transientFailures = 0;
            pausedUntilNanos = 0L;
            transientPauseLogged.set(false);
            clientAuthLogged.set(false);
        }
    }

    /**
     * @return {@code false} while the circuit pause is active; clears an expired pause inline.
     */
    public boolean isRemoteCallAllowed() {
        synchronized (lock) {
            long now = System.nanoTime();
            if (pausedUntilNanos != 0L && now >= pausedUntilNanos) {
                pausedUntilNanos = 0L;
                transientFailures = 0;
                transientPauseLogged.set(false);
            }
            return pausedUntilNanos == 0L;
        }
    }

    public void record(ModerationApiResult result) {
        synchronized (lock) {
            if (result.kind() == ModerationApiResult.Kind.CLIENT_AUTH) {
                if (clientAuthLogged.compareAndSet(false, true)) {
                    Logger.warn("Chat moderation API rejected the key (HTTP 401/403). Update chat_moderation.api.apiKey in performanceConfig.yml.");
                }
                return;
            }

            if (result.tripsTransientBreaker()) {
                transientFailures++;
                if (transientFailures >= TRANSIENT_TRIP_THRESHOLD) {
                    pausedUntilNanos = System.nanoTime() + PAUSE_NANOS;
                    transientFailures = 0;
                    if (transientPauseLogged.compareAndSet(false, true)) {
                        Logger.warn("Chat moderation paused for "
                                + TimeUnit.NANOSECONDS.toSeconds(PAUSE_NANOS)
                                + "s after repeated platform errors. Messages are not scanned until the pause ends or you run /np reload.");
                    }
                }
                return;
            }

            transientFailures = 0;
            pausedUntilNanos = 0L;
            transientPauseLogged.set(false);
            clientAuthLogged.set(false);
        }
    }
}
