import java.util.LinkedList;
import java.util.Queue;

class LeakyBucket {
    private final int capacity;
    private final int leakRatePerSec;
    private final Queue<Long> bucket = new LinkedList<>();
    private long lastLeakTime;

    public LeakyBucket(int capacity, int leakRatePerSec) {
        this.capacity = capacity;
        this.leakRatePerSec = leakRatePerSec;
        this.lastLeakTime = System.nanoTime();
    }

    public synchronized boolean allowRequest() {
        leak();

        if (bucket.size() < capacity) {
            bucket.offer(System.nanoTime()); // accept request
            return true;
        }
        return false; // rejected
    }

    private void leak() {
        long now = System.nanoTime();
        long elapsedSeconds = (now - lastLeakTime) / 1_000_000_000;

        if (elapsedSeconds > 0) {
            int leaks = (int)(elapsedSeconds * leakRatePerSec);
            while (leaks-- > 0 && !bucket.isEmpty()) {
                bucket.poll();
            }
            lastLeakTime = now;
        }
    }
}
