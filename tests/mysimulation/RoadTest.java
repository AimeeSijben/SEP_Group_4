
package mysimulation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that spawnCars enqueues a Car with the correct timestamp.
 */
public class RoadTest {

    @Test
    void spawnCars_WhenArrivalRateIsOne_ShouldEnqueueCarWithCorrectTimestamp() {
        // Given an arrivalRate of 1.0, spawnCars always adds to each queue
        Road road = new Road("TestRoad", 1.0);
        long tick = 5L;

        // When
        road.spawnCars(tick);

        // Then
        Car car = road.peek(Road.Direction.POSITIVE);
        assertNotNull(car, "Positive queue should have a car");
        assertEquals(tick, car.getArrivalTick(), "Car timestamp should match the tick");
    }

    @Test
    void spawnCars_WhenArrivalRateIsZero_ShouldNotEnqueueAnyCars() {
        // Given a Road with arrivalRate = 0.0 (no cars ever spawn)
        Road road = new Road("TestRoad", 0.0);
        long tick = 10L;

        // When spawning cars
        road.spawnCars(tick);

        // Then neither queue should receive a Car
        assertNull(road.peek(Road.Direction.POSITIVE), "Positive queue should remain empty");
        assertNull(road.peek(Road.Direction.NEGATIVE), "Negative queue should remain empty");
        assertEquals(0, road.getQueueLength(null), "Total queue length should be zero");
    }
}
