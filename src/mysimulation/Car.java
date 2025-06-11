package mysimulation;

public class Car {
    private static long idCounter = 0;
    private final long id;
    private final long arrivalTick;
    private Direction direction;

    public Car(long tick) {
        this.id = idCounter++;
        this.arrivalTick = tick;
        this.direction = Direction.NORTH;
    }

    public Car() {
        this.id = idCounter++;
        this.arrivalTick = 0;
        this.direction = Direction.NORTH;
        // TODO implement
    }

    public Car(long tick, Direction dir) {
        this.id = idCounter++;
        this.arrivalTick = 0;// TODO implement
        this.direction = dir;

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

    public long timeAlive(long currentTime) {
        if (currentTime < arrivalTick) {
            // ERROR
            return 0;
        }
        return currentTime - arrivalTick;
    }

    public Direction getdir() {
        return direction;
    }

    public void setdir(Direction dir) {
        this.direction = dir;
    }
}
