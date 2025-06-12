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
    
    public static void resetIdCounter() {
        idCounter = 0;
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

    public Direction rotateRight(Direction dir) {
        return switch (dir) {
            case NORTH -> Direction.WEST;
            case EAST -> Direction.SOUTH;
            case SOUTH -> Direction.EAST;
            case WEST -> Direction.NORTH;
        };
    }

    boolean hasTrafficLight(Cell in) {
        return in.getTrafficLight() != null;
    }

    boolean getStopTrafficLightSignal(Cell in) {
        return in.getTrafficLight() != null && in.getTrafficLight().getState() == TrafficLight.State.RED;
    }

    boolean hasCar(Cell in) {
        return in.getCar() != null;
    }

    Cell getCellDirection(Cell north, Cell east, Cell south, Cell west, Direction dir) {
        return switch (dir) {
            case NORTH -> north;
            case EAST -> east;
            case SOUTH -> south;
            case WEST -> west;
        };
    }

    public Position calculateDeltaPos(Cell north, Cell east, Cell south, Cell west) {
        // directions are given in glogal directions eg as defined in Position.java
        Direction localRight = rotateRight(this.direction);
        // CHECK: if no red traffic light
        Cell lightTarget = getCellDirection(north, east, south, west, localRight);
        boolean stopSignal = getStopTrafficLightSignal(lightTarget);
        // CHECK: if no car in front of this car
        Cell frontCell = getCellDirection(north, east, south, west, this.direction);
        boolean validNewSpot = !hasCar(frontCell);// empty spot
        if (!stopSignal && validNewSpot) {
            return this.direction.move();
        }
        return new Position(0, 0);// no movement
    }
}
