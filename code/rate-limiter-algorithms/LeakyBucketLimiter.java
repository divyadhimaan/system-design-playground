import java.text.SimpleDateFormat;
import java.util.*;

class LeakyBucketLimiter {
    private final int capacity;          // Max bucket size
    private final int leakRatePerSec;    // Leak rate (requests removed per sec)
    private final Queue<Long> bucket = new LinkedList<>();
    private long lastLeakTime;

    public LeakyBucketLimiter(int capacity, int leakRatePerSec) {
        this.capacity = capacity;
        this.leakRatePerSec = leakRatePerSec;
        this.lastLeakTime = System.currentTimeMillis();
    }

    public synchronized boolean allowRequest() {
        leak();

        if (bucket.size() < capacity) {
            bucket.offer(System.currentTimeMillis()); // accept request
            return true;
        }
        return false; // rejected
    }

    private void leak() {
        long now = System.currentTimeMillis();
        long elapsedSeconds = (now - lastLeakTime) / 1000;

        if (elapsedSeconds > 0) {
            int leaks = (int) (elapsedSeconds * leakRatePerSec);
            while (leaks-- > 0 && !bucket.isEmpty()) {
                bucket.poll();
            }
            // advance lastLeakTime by elapsed seconds (not reset fully to now)
            lastLeakTime += elapsedSeconds * 1000;
        }
    }

    private static String formatTime(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        return sdf.format(new Date(millis));
    }

    public static void main(String[] args) throws InterruptedException {
        LeakyBucketLimiter limiter = new LeakyBucketLimiter(5, 2); // capacity=5, leak=2 req/sec

        for (int i = 0; i < 12; i++) {
            long now = System.currentTimeMillis();
            boolean allowed = limiter.allowRequest();
            System.out.println("[" + formatTime(now) + "] Request " + i + " allowed? " + allowed);
            Thread.sleep(300); // simulate time gap
        }
    }
}

//| **Time**     | **Request** | **Bucket Size (after req)** | **Leak Activity**                                                                   | **Allowed?** |
//| ------------ | ----------- | --------------------------- | ----------------------------------------------------------------------------------- | ------------ |
//| 21:47:30.800 | 0           | 1                           | Bucket empty initially → accepts                                                    | ✅ true       |
//| 21:47:31.143 | 1           | 2                           | Still < capacity                                                                    | ✅ true       |
//| 21:47:31.448 | 2           | 3                           |                                                                                     | ✅ true       |
//| 21:47:31.753 | 3           | 4                           |                                                                                     | ✅ true       |
//| 21:47:32.058 | 4           | 5 (full now)                |                                                                                     | ✅ true       |
//| 21:47:32.363 | 5           | 4                           | \~1 sec passed since first req → leak 2 slots → frees 2 → accept                    | ✅ true       |
//| 21:47:32.669 | 6           | 5                           | Fills again to capacity                                                             | ✅ true       |
//| 21:47:32.975 | 7           | 5                           | No new leaks yet (only \~300 ms passed) → capacity full but just enough space for 1 | ✅ true       |
//| 21:47:33.281 | 8           | 5                           | Still full                                                                          | ✅ true       |
//| 21:47:33.587 | 9           | 5 (overflow)                | No leak yet → request rejected                                                      | ❌ false      |
//| 21:47:33.891 | 10          | 4                           | At this moment, >1 sec elapsed again → leaks 2 → frees space                        | ✅ true       |
//| 21:47:34.197 | 11          | 5                           | Accepts, bucket full again                                                          | ✅ true       |
