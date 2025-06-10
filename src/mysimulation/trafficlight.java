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

    public TrafficLight(int cycleLength, Direction dir) {
        if (cycleLength <= 0) {
            throw new IllegalArgumentException("Cycle length must be positive.");
        }
        this.cycleLength = cycleLength;
        this.currentState = State.RED; // Default initial state
        direction = dir;
    }

    boolean isConflict(TrafficLight a, TrafficLight b) {
        return false;
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
