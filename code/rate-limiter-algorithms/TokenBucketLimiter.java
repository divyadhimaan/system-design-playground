import java.text.SimpleDateFormat;
import java.util.Date;

class TokenBucketLimiter {
    private final int capacity;          // max number of tokens
    private final int refillRatePerSec;  // tokens added per second
    private int tokens;                  // current tokens
    private long lastRefillTime;         // last refill timestamp (ms)

    public TokenBucketLimiter(int capacity, int refillRatePerSec) {
        this.capacity = capacity;
        this.refillRatePerSec = refillRatePerSec;
        this.tokens = capacity; // start full
        this.lastRefillTime = System.currentTimeMillis();
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
        long now = System.currentTimeMillis();
        long elapsedSeconds = (now - lastRefillTime) / 1000;

        if (elapsedSeconds > 0) {
            int newTokens = (int) (elapsedSeconds * refillRatePerSec);
            tokens = Math.min(capacity, tokens + newTokens);
            lastRefillTime += elapsedSeconds * 1000; // advance by elapsed time
        }
    }

    private static String formatTime(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        return sdf.format(new Date(millis));
    }

    // Demo
    public static void main(String[] args) throws InterruptedException {
        TokenBucketLimiter limiter = new TokenBucketLimiter(5, 2);
        // capacity = 5, refill = 2 tokens/sec

        for (int i = 0; i < 12; i++) {
            long now = System.currentTimeMillis();
            boolean allowed = limiter.allowRequest();
            System.out.println("[" + formatTime(now) + "] Request " + i +
                    " allowed? " + allowed +
                    " | tokens left = " + limiter.tokens);
            Thread.sleep(300); // simulate gap between requests
        }
    }
}
