package mysimulation;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public Direction opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
        };
    }

    public Position move() {
        return switch (this) {
            case NORTH -> new Position(-1, 0);
            case SOUTH -> new Position(1, 0);
            case EAST -> new Position(0, 1);
            case WEST -> new Position(0, -1);
        };
    }

}
