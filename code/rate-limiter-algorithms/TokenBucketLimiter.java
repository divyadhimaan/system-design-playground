class TokenBucketLimiter {
    private final int capacity;
    private final int refillRatePerSec;
    private int tokens;
    private long lastRefillTime;

    public TokenBucketLimiter(int capacity, int refillRatePerSec) {
        this.capacity = capacity;
        this.refillRatePerSec = refillRatePerSec;
        this.tokens = capacity;
        this.lastRefillTime = System.nanoTime();
    }

    public synchronized boolean allowRequest() {
        refill();

        if (tokens > 0) {
            tokens--;
            return true; // allowed
        }
        return false; // rejected
    }

    private void refill() {
        long now = System.nanoTime();
        long elapsedSeconds = (now - lastRefillTime) / 1_000_000_000;
        if (elapsedSeconds > 0) {
            int newTokens = (int)(elapsedSeconds * refillRatePerSec);
            tokens = Math.min(capacity, tokens + newTokens);
            lastRefillTime = now;
        }
    }
}

