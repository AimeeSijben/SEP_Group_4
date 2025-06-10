package mysimulation;

import java.awt.Color;
import java.awt.Graphics;

public class Car{
    private Cell cell;
    private Direction dir;
    private int speed;
    private final int updatedSpeed;
    private Color carColor;
    private Trafficlight associatedTrafficlight;
    private int stopDistance = 1;

    // new car entering the grid
    public Car(Cell cell, Direction dir, int updatedSpeed, Color carColor){
        this.cell = initialCell;
        this.dir = initialDir;
        this.updatedSpeed = updatedSpeed;
        this.speed = updatedSpeed;
        this.carColor = carColor;
    }

    // check if the car should stop
    private boolean shouldStop(Cell potentialNextCell, Trafficlight light) {
        if (light == null || !light.isRed()){
            return false;
        }

        int LightStopX = light.getStopX();
        int lightStopY = light.getStopY();

        // calc a target stop coordinate
        int targetStopX = lightStopX;
        int targetStopY = lightStopY;

    switch(dir){
        case EAST: targetStopX -= stopDistance; break;
        case WEST: targetStopX += stopDistance; break;
        case NORTH: targetStopX += stopDistance; break;
        case SOUTH: targetStopX -= stopDistance; break;
    }

    //check if ca's potential next cell would put the car at ot past the targeted cell
    switch(dir){
        case EAST: return potentialNextCell.getX() >= targetStopX;
        case WEST: return potentialNextCell.getX() <= targetStopX;
        case NORTH: return potentialNextCell.getY() <= targetStopY;
        case SOUTH: return potentialNextCell.getY() >= targetStopY;
        default: return false;
    }
}

public void move(Grid grid){
    Cell nextCell = getNextCell(grid);

    boolean canMoveTick = true;
    int currentSpeed = updatedSpeed;

    // Check Traffic Light
    if (associatedTrafficlight != null){
        //ensure light is assigned
        if (shouldStop(nextCell, associatedTrafficlight)){
            //stop due to red light
            currentSpeed = 0;
            canMoveTick = false;
        }
    }

    //check for other cars, if car should not be stopping
    if (canMoveTick && currentSpeed > 0 && nextCell != null){
        if (!grid.isCellEmpty(nextCell)){
            //if cell is occupied by another car, or this car if speed > 1
            if (grid.getCellOccupant(nextCell) != this){
                currentSpeed = 0; //stop due to another car
                canMoveTick = false;
            }
        }
    }

    this.speed = currentSpeed

    //moving the car if allowed
    if (canMoveTick && this.speed > 0 && nextCell != null){
        grid.setCellOccupant(cell, null); //empty old cell
        this.cell = potentialNextCell; //update the car's position
        grid.setCellOccuppant(cell, this); //occupy new cell with car
    }
    //if nextCell is null, the car will go off grid --> main loop removes the car
}

//determine next cell based on direction and speed
public Cell getNextCell(Grid grid){
    int x = cell.getX();
    int y = cell.getY();

    switch (dir){
        case NORTH: nextY -= speed; break;
        case SOUTH: nextY += speed; break;
        case EAST: nextX -= speed; break;
        case WEST: nextY += speed; break;
    }

    if (nextX >= 0 && nextX < grid.getCols() && nextY >= 0 && nextY < grid.getRows()) {
        return grid.getCell(nextX, nextY);
    }
    return null; //car's off grid
}

    public void draw (Graphics g, int cellsize){
        int xPix = cell.getX()*cellsize;
        int yPix = cell.getY()*cellsize;

        g.setColor(carColor);
        g.fillRect(xPix + 2, yPix + 2, cellsize - 4, cellsize - 4); 
    }

    public Cell getCurrentCell(){return cell;}
    public Direction getCurrentDir(){return dir;}
    public int getSpeed(){ return speed;}
    public Color getCarColor(){ return carColor;}
}
