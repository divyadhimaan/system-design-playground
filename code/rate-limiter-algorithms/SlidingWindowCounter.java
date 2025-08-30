import java.util.concurrent.ConcurrentHashMap;

class SlidingWindowCounter {
    private final int windowSizeInSec;
    private final int maxRequests;
    private final ConcurrentHashMap<Long, Integer> counter = new ConcurrentHashMap<>();

    public SlidingWindowCounter(int windowSizeInSec, int maxRequests) {
        this.windowSizeInSec = windowSizeInSec;
        this.maxRequests = maxRequests;
    }

    public synchronized boolean allowRequest() {
        long currentWindow = System.currentTimeMillis() / 1000; // in sec
        counter.putIfAbsent(currentWindow, 0);
        counter.put(currentWindow, counter.get(currentWindow) + 1);

        // Current & Previous window counts
        int currentCount = counter.get(currentWindow);
        int prevCount = counter.getOrDefault(currentWindow - 1, 0);

        // Remaining time fraction in current window
        double elapsed = (System.currentTimeMillis() / 1000.0) % windowSizeInSec;
        double weight = (windowSizeInSec - elapsed) / windowSizeInSec;

        double effectiveCount = currentCount + (prevCount * weight);

        return effectiveCount <= maxRequests;
    }
    public static void main(String[] args) throws InterruptedException {
        SlidingWindowCounter limiter = new SlidingWindowCounter(60, 5);

        for (int i = 0; i < 10; i++) {
            System.out.println("Request " + i + " allowed? " + limiter.allowRequest());
            Thread.sleep(500); // 0.5 sec gap
        }
    }
}
