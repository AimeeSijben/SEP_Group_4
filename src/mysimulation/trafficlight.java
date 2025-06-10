package mysimulation;

public class TrafficLight {
    enum State {
        RED, GREEN
    }

    State currentState;
    int cycleLength;
    Direction direction;

    boolean iseven(int x) {
        return (x & 1) == 0;
    }

    int max(int a,int b) {  
        return a>b?a:b;
    }
    int min(int a, int b) {
        return a > b ? b : a;
    }

    boolean isPerpendicular(Direction a, Direction b) {

        return (a == Direction.NORTH && (b == Direction.EAST || b == Direction.WEST)) ||
                (a == Direction.SOUTH && (b == Direction.EAST || b == Direction.WEST)) ||
                (b == Direction.NORTH && (a == Direction.EAST || a == Direction.WEST)) ||
                (b == Direction.NORTH && (a == Direction.EAST || a == Direction.WEST));

    }

    public TrafficLight(int cycleLength, Direction dir) {
        if (cycleLength <= 0) {
            throw new IllegalArgumentException("Cycle length must be positive.");
        }
        this.cycleLength = cycleLength;
        this.currentState = State.RED; // Default initial state
        direction = dir;
    }

    boolean isConflict(TrafficLight a, TrafficLight b) {
        int maxcycle = max(a.cycleLength, b.cycleLength);
        int mincycle = min(a.cycleLength, b.cycleLength);

        return isPerpendicular(a.direction, b.direction) && (maxcycle % mincycle == 0);
    }

    void setState(State newState) {
        currentState = newState;// TODO: check situation
    }

    public State getState() {
        return currentState;
    }

    void update(int timer) {
        State newState = iseven(timer / cycleLength) ? State.GREEN : State.RED;
        if (currentState != newState) {
            setState(newState);
        }

    }
}
