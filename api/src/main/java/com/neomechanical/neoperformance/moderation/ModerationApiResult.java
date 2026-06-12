package com.neomechanical.neoperformance.moderation;

/**
 * Outcome of a remote moderation request. Used by the circuit breaker to decide what counts as
 * infrastructure failure vs. a normal moderation decision.
 */
public record ModerationApiResult(Kind kind) {

    public enum Kind {
        /** Policy says the message should be blocked. */
        FLAGGED,
        /** Message is allowed under policy. */
        CLEAR,
        /** Network, timeout, rate limit, or 5xx — counts toward opening the circuit. */
        TRANSIENT_TRANSPORT,
        /** 401 / 403 etc. — does not open the long pause; operators should fix the API key. */
        CLIENT_AUTH
    }

    public boolean isFlagged() {
        return kind == Kind.FLAGGED;
    }

    public boolean tripsTransientBreaker() {
        return kind == Kind.TRANSIENT_TRANSPORT;
    }

    public static ModerationApiResult flagged() {
        return new ModerationApiResult(Kind.FLAGGED);
    }

    public static ModerationApiResult clear() {
        return new ModerationApiResult(Kind.CLEAR);
    }

    public static ModerationApiResult transientTransport() {
        return new ModerationApiResult(Kind.TRANSIENT_TRANSPORT);
    }

    public static ModerationApiResult clientAuth() {
        return new ModerationApiResult(Kind.CLIENT_AUTH);
    }
}
