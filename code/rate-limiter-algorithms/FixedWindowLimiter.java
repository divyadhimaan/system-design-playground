import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowLimiter {
    private final int maxRequests;
    private final long windowSizeInMillis;
    private long windowStart;
    private final AtomicInteger requestCount;

    public FixedWindowLimiter(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.windowStart = System.currentTimeMillis();
        this.requestCount = new AtomicInteger(0);
    }

    public synchronized boolean allowRequest() {
        long currentTime = System.currentTimeMillis();

        // Reset window if expired
        if (currentTime - windowStart >= windowSizeInMillis) {
            windowStart = currentTime;
            requestCount.set(0);
        }

        if (requestCount.get() < maxRequests) {
            requestCount.incrementAndGet();
            return true; // request allowed
        }
        return false; // request rejected
    }

    public static void main(String[] args) throws InterruptedException {
        FixedWindowLimiter limiter = new FixedWindowLimiter(5, 1000); // 5 req/sec

        for (int i = 1; i <= 10; i++) {
            if (limiter.allowRequest()) {
                System.out.println("Request " + i + " allowed");
            } else {
                System.out.println("Request " + i + " rejected");
            }
            Thread.sleep(100); // simulate request interval
        }
    }
}
