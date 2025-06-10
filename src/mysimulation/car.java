package mysimulation;

public class Car {
    private static long nextCarId = 0;
    private final long carId;
    private final long arrivalTick;

    public Car(long arrivalTick) {
        this.carId = nextCarId++;
        this.arrivalTick = arrivalTick;
    }

    public long getCarId() {
        return carId;
    }

    public long getArrivalTick() {
        return arrivalTick;
    }

    @Override
    public String toString() {
        return "Car-" + carId + " (Arrived: " + arrivalTick + ")";
    }
}
