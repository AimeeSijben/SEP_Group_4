package mysimulation;

public class Car {
    private static long idCounter = 0;
    private final long id;
    private final long arrivalTick;

    public Car(long tick) {
        this.id = idCounter++;
        this.arrivalTick = tick;
    }

    public Car() {
        this.id = idCounter++;
        this.arrivalTick = 0;
        // TODO implement
    }

    public Car(long tick, Direction dir) {
        this.id = idCounter++;
        this.arrivalTick = 0;// TODO implement

    }

    public long getId() {
        return id;
    }

    public long getArrivalTick() {
        return arrivalTick;
    }

    @Override
    public String toString() {
        return "Car-" + id + "arrived at: arrivalTick";
    }

    public Direction getdir() {
        return Direction.NORTH;
    }
}
