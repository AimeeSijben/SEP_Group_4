// To compile:
//   javac -cp path/to/junit-jupiter-api-5.x.x.jar:. src/mysimulation/Road.java test/mysimulation/RoadTest.java
// To run tests and collect coverage (using JaCoCo agent + CLI):
//   java -javaagent:path/to/jacocoagent.jar=destfile=jacoco.exec \
//        -jar path/to/junit-platform-console-standalone.jar --class-path . --scan-class-path
//   java -jar path/to/jacococli.jar report jacoco.exec \
//        --classfiles src --sourcefiles src --csv coverage.csv
//   grep "mysimulation/Road.java" coverage.csv

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
}
