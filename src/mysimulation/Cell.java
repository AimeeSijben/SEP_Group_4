package mysimulation;

public class Cell {
    public enum Type {
        EMPTY,
        SPAWN,
        ROADUP,
        ROADDOWN,
        ROADLEFT,
        ROADRIGHT,
        INTERSECTION,
        CAR,
        TRAFICLIGHT
    }

    private Type type;
    private Car car;
    private TrafficLight TrafficLight;

    public Cell() {
        this.type = Type.EMPTY;
        this.car = null;
        this.TrafficLight = null;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        if (car != null) {
            return Type.CAR;
        } else if (TrafficLight != null) {
            return Type.TRAFICLIGHT;
        } else {
            return type;
        }
    }

    public void setCar(Car Car) {
        this.car = Car;
    }

    public Car getCar() {
        return this.car;
    }

    public void setTrafficLight(TrafficLight TrafficLight) {
        this.TrafficLight = TrafficLight;
    }

    public TrafficLight getTrafficLight() {
        return this.TrafficLight;
    }

    public String toString() {
        if (car != null) {
            return "C";
        } else if (TrafficLight != null) {
            return "T";
        } else
            return switch (type) {
                case ROADUP -> "↑";
                case ROADDOWN -> "↓";
                case ROADLEFT -> "←";
                case ROADRIGHT -> "→";
                case INTERSECTION -> "X";
                case SPAWN -> "S";
                case CAR -> "C";
                default -> "E";

            };
    }
}
