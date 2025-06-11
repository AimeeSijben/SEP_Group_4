package mysimulation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

class TrafficLightTest {

    private TrafficLight light;

    @BeforeEach
    void setUp() {
        light = new TrafficLight(10, Direction.NORTH);
    }

    // Test constructor and initial state
    @Test
    void constructor_PositiveCycleLength_SetsInitialStateToRed() {
        assertEquals(TrafficLight.State.RED, light.getState());
        assertEquals(10, light.cycleLength);
        assertEquals(Direction.NORTH, light.direction);
    }

    @Test
    void constructor_NonPositiveCycleLength_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TrafficLight(0, Direction.NORTH);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new TrafficLight(-5, Direction.SOUTH);
        });
    }

    // Test iseven() method
    @ParameterizedTest
    @ValueSource(ints = { 0, 2, 4, 6, 8, -2, -4 })
    void iseven_EvenNumbers_ReturnsTrue(int number) {
        assertTrue(light.iseven(number));
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 3, 5, 7, -1, -3 })
    void iseven_OddNumbers_ReturnsFalse(int number) {
        assertFalse(light.iseven(number));
    }

    // Test max() method
    @Test
    void max_FirstArgumentLarger_ReturnsFirst() {
        assertEquals(5, light.max(5, 3));
    }

    @Test
    void max_SecondArgumentLarger_ReturnsSecond() {
        assertEquals(7, light.max(3, 7));
    }

    @Test
    void max_EqualArguments_ReturnsEither() {
        assertEquals(4, light.max(4, 4));
    }

    // Test min() method
    @Test
    void min_FirstArgumentSmaller_ReturnsFirst() {
        assertEquals(2, light.min(2, 5));
    }

    @Test
    void min_SecondArgumentSmaller_ReturnsSecond() {
        assertEquals(1, light.min(5, 1));
    }

    @Test
    void min_EqualArguments_ReturnsEither() {
        assertEquals(4, light.min(4, 4));
    }

    // Test isPerpendicular() method
    @ParameterizedTest
    @CsvSource({
            "NORTH, EAST, true",
            "NORTH, WEST, true",
            "SOUTH, EAST, true",
            "SOUTH, WEST, true",
            "EAST, NORTH, true",
            "WEST, NORTH, true",
            "NORTH, SOUTH, false",
            "EAST, WEST, false",
            "SOUTH, SOUTH, false"
    })
    void isPerpendicular_VariousDirections_ReturnsCorrectValue(Direction a, Direction b, boolean expected) {
        assertEquals(expected, light.isPerpendicular(a, b));
    }

    // Test isConflict() method
    @Test
    void isConflict_PerpendicularWithDivisibleCycles_ReturnsTrue() {
        TrafficLight light1 = new TrafficLight(10, Direction.NORTH);
        TrafficLight light2 = new TrafficLight(5, Direction.EAST);
        assertTrue(light.isConflict(light1, light2));
    }

    @Test
    void isConflict_ParallelDirections_ReturnsFalse() {
        TrafficLight light1 = new TrafficLight(10, Direction.NORTH);
        TrafficLight light2 = new TrafficLight(5, Direction.SOUTH);
        assertFalse(light.isConflict(light1, light2));
    }

    @Test
    void isConflict_PerpendicularWithNonDivisibleCycles_ReturnsFalse() {
        TrafficLight light1 = new TrafficLight(10, Direction.NORTH);
        TrafficLight light2 = new TrafficLight(7, Direction.EAST);
        assertFalse(light.isConflict(light1, light2));
    }

    // Test update() method
    @Test
    void update_TimerAtHalfCycle_ChangesToGreen() {
        light.update(5); // Half cycle (10/2)
        assertEquals(TrafficLight.State.GREEN, light.getState());
    }

    @Test
    void update_TimerAtFullCycle_ChangesToRed() {
        light.update(10); // Full cycle
        assertEquals(TrafficLight.State.RED, light.getState());
    }

    @Test
    void update_TimerAtMultipleCycles_ChangesStateAppropriately() {
        light.update(15); // 1.5 cycles
        assertEquals(TrafficLight.State.GREEN, light.getState());

        light.update(20); // 2 full cycles
        assertEquals(TrafficLight.State.RED, light.getState());
    }

    // Test setState() and getState() methods
    @Test
    void setState_NewState_UpdatesCurrentState() {
        light.setState(TrafficLight.State.GREEN);
        assertEquals(TrafficLight.State.GREEN, light.getState());

        light.setState(TrafficLight.State.RED);
        assertEquals(TrafficLight.State.RED, light.getState());
    }
}