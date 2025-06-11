package mysimulation;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

/**
 * A Road with two one‐way lanes (opposite directions).
 * Cars arrive randomly on each lane and wait in a FIFO queue.
 */
public class Road {
    public enum Direction {
        POSITIVE, NEGATIVE
    }

    private final String name;
    private final double arrivalRate; // probability per tick of a new car
    private final Random rng;

    // one queue for each direction
    private final Queue<Car> queuePos = new ArrayDeque<>();
    private final Queue<Car> queueNeg = new ArrayDeque<>();

    /**
     * @param name        a label for logging (e.g. "North–South")
     * @param arrivalRate 0.0–1.0 chance per tick of spawning a Car in each lane
     */
    public Road(String name, double arrivalRate) {
        this.name = name;
        this.arrivalRate = arrivalRate;
        this.rng = new Random();
    }

    /** @return the name of this road (for logging) */
    public String getName() {
        return name;
    }

    /**
     * On each tick, possibly spawn one new Car in each direction queue.
     * 
     * @param tick the current tick number (used to timestamp arrivals)
     */
    public void spawnCars(long tick) {
        spawnIn(queuePos, tick);
        spawnIn(queueNeg, tick);
    }

    private void spawnIn(Queue<Car> q, long tick) {
        if (rng.nextDouble() < arrivalRate) {
            q.add(new Car(tick));
        }
    }

    /**
     * Serve up to capacity Cars from the green lanes.
     * If both directions are green, they'll each get up to capacity.
     * 
     * @param dir      which lane(s) have green: POSITIVE, NEGATIVE or BOTH
     * @param capacity max Cars to dequeue per green lane
     * @return total Cars that actually crossed
     */
    public int serve(Direction dir, int capacity) {
        int served = 0;
        if (dir == Direction.POSITIVE || dir == null) {
            served += dequeueUpTo(queuePos, capacity);
        }
        if (dir == Direction.NEGATIVE || dir == null) {
            served += dequeueUpTo(queueNeg, capacity);
        }
        return served;
    }

    /** helper to dequeue up to n Cars */
    private int dequeueUpTo(Queue<Car> q, int n) {
        int cnt = 0;
        while (cnt < n && !q.isEmpty()) {
            q.poll();
            cnt++;
        }
        return cnt;
    }

    /**
     * @param dir which lane to measure; null for total
     * @return current queue length
     */
    public int getQueueLength(Direction dir) {
        if (dir == Direction.POSITIVE)
            return queuePos.size();
        if (dir == Direction.NEGATIVE)
            return queueNeg.size();
        return queuePos.size() + queueNeg.size();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [→:%d  ←:%d]",
                name, queuePos.size(), queueNeg.size());
    }
}