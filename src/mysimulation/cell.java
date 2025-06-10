package mysimulation;

public class cell {
    public enum Type {
        EMPTY,
        SPAWN,
        ROADUP,
        ROADDOWN,
        ROADLEFT,
        ROADRIGHT,
        INTERSECTION
    }

    private Type type;

    public cell() {
        this.type = Type.EMPTY;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        return switch (type) {
            case ROADUP -> "↑";
            case ROADDOWN -> "↓";
            case ROADLEFT -> "←";
            case ROADRIGHT -> "→";
            case INTERSECTION -> "X";
            case SPAWN -> "S";
            default -> "E";
        };
    }
}
