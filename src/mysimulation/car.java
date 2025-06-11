package mysimulation;

public class Car {
    private static long idCounter = 0;
    private final long id;
    private final long arrivalTick;

    public Car(long tick) {
        this.id = idCounter++; //Assign ID
        this.arrivalTick = tick; //Record arrival time
    }

    public long getId() {
        return id;
    }

    public long getArrivalTick() {
        return arrivalTick;
    }

    @Override
    public String toString() {
        return "Car#" + id + "(arrived:" + arrivalTick + ")";
    }
}
