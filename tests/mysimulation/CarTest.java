package mysimulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CarTest {
    private Road road;
    private TrafficLight trafficLight;
    private final int cycleLength = 4;
    private final double arrivalRate = 1.0; // Ensure car spawns every tick for testing

    @BeforeEach
    void setUp() {
        road = new Road("TestRoad", arrivalRate);
        trafficLight = new TrafficLight(cycleLength, Direction.NORTH);
    }

    @Test
    void testDequeueCarGREEN() {
        // Spawn a car and set light to green
        road.spawnCars(0); // Spawn a car at tick 0
        assertEquals(1, road.getQueueLength(Road.Direction.POSITIVE), "Car should be in the queue");
        trafficLight.update(0); // Tick 0: light is green (even tick)
        assertEquals(TrafficLight.State.GREEN, trafficLight.getState(), "Light should be green");

        // try to serve the road with green light
        int served = road.serve(Road.Direction.POSITIVE, 1);
        assertEquals(1, served, "One car should be served");
        assertEquals(0, road.getQueueLength(Road.Direction.POSITIVE), "Queue should be empty");
    }

    @Test
    void testNotDequeueCarRED() {
        // Spawn a car and set light to red
        road.spawnCars(0); // Spawn a car at tick 0
        assertEquals(1, road.getQueueLength(Road.Direction.POSITIVE), "Car should be in the queue");
        trafficLight.update(1);
        assertEquals(TrafficLight.State.RED, trafficLight.getState(), "Light should be red");

        // try to serve the road with red light
        int served = road.serve(Road.Direction.POSITIVE, 1);
        assertEquals(0, served, "No car should be served");
        assertEquals(1, road.getQueueLength(Road.Direction.POSITIVE), "Car should stay in the queue");
    }

    @Test
    void testQueueOrderPreserved() {
        // Spawn two cars, at different ticks
        road.spawnCars(0);
        road.spawnCars(1);
        assertEquals(2, road.getQueueLength(Road.Direction.POSITIVE), "Two cars should be in the queue");

        // Serve one car with green light
        trafficLight.update(0); // Tick 0: light is green
        int served = road.serve(Road.Direction.POSITIVE, 1);
        assertEquals(1, served, "One car should be served");
        assertEquals(1, road.getQueueLength(Road.Direction.POSITIVE), "One car should be in the queue");

        // Serve a second car
        served = road.serve(Road.Direction.POSITIVE, 1);
        assertEquals(1, served, "Second car should be served");
        assertEquals(0, road.getQueueLength(Road.Direction.POSITIVE), "The queue should be empty");
    }

    @Test
    void testCarId() {
        Car car1 = new Car(0);
        Car car2 = new Car(1);
        Car car3 = new Car(2);

        assertEquals(0, car1.getId(), "First car should have ID 0");
        assertEquals(1, car2.getId(), "Second car should have ID 1");
        assertEquals(2, car3.getId(), "Third car should have ID 2");
        assertNotEquals(car1.getId(), car2.getId(), "Car IDs should be different");
        assertNotEquals(car2.getId(), car3.getId(), "Car IDs should be different");
        assertNotEquals(car1.getId(), car3.getId(), "Car IDs should be different");
    }
}
