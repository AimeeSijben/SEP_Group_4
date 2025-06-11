package mysimulation;

import java.util.ArrayList;
import java.util.List;

public class Car{
    private static long idCounter = 0;
    private final long id;
    private final long arrivalTick;
    private final Direction dir;

    public Car(long tick, Direction dir){
        this.id = idCounter++;
        this.arrivalTick = tick;
        this.dir = dir;
    }

    public Direction getdir(){
        return this.dir;
    }

    public long getId(){
        return id;
    }

    public long getArrivalTick(){
        return arrivalTick;
    }

    @Override
    public String toString(){
        return "Car-" + id + "arrived at: arrivalTick";
    }
}
