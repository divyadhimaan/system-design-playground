import java.text.SimpleDateFormat;
import java.util.*;

public class SlidingWindowLogLimiter {
    private final int maxRequests;
    private final long windowSizeInMillis;
    private final Deque<Long> timestamps;

    public SlidingWindowLogLimiter(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.timestamps = new ArrayDeque<>();
    }

    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();

        // Remove timestamps older than window
        while (!timestamps.isEmpty() && (now - timestamps.peekFirst()) >= windowSizeInMillis) {
            timestamps.pollFirst();
        }

        if (timestamps.size() < maxRequests) {
            timestamps.addLast(now);
            return true; // allow request
        } else {
            return false; // reject request
        }
    }

    private static String formatTime(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        return sdf.format(new Date(millis));
    }

    public static void main(String[] args) throws InterruptedException {
        SlidingWindowLogLimiter limiter = new SlidingWindowLogLimiter(5, 60000); // 5 req/min

        for (int i = 0; i < 7; i++) {
            long now = System.currentTimeMillis();
            boolean allowed = limiter.allowRequest();
            System.out.println("[" + formatTime(now) + "] Request " + i + " allowed? " + allowed);
            Thread.sleep(500); // simulate time gap
        }
    }
}
