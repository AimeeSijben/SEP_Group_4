package mysimulation;

import mysimulation.Trafficlight.State;

public class Car {
    private Cell currentCell;
    private Direction currentDirection;
    private int speed;
    private final int normalSpeed;
    private Trafficlight associatedTrafficLight;
    private int stopDistance = 1;

    public Car(Cell initialCell, Direction initialDirection, int normalSpeed) {
        this.currentCell = initialCell;
        this.currentDirection = initialDirection;
        this.normalSpeed = normalSpeed;
        this.speed = normalSpeed;
    }

    public void setAssociatedTrafficLight(Trafficlight light, int stopDistance) {
        this.associatedTrafficLight = light;
        this.stopDistance = stopDistance;
    }

    private boolean shouldStop(Cell potentialNextCell, Trafficlight light) {
        if (light == null || !light.isRed()) {
            return false;
        }

        State lightStopX = light.getState();
        State lightStopY = light.getState();

        State targetStopX = lightStopX;
        State targetStopY = lightStopY;

        switch (currentDirection) {
            case EAST: targetStopX -= stopDistance; break;
            case WEST: targetStopX += stopDistance; break;
            case NORTH: targetStopY += stopDistance; break;
            case SOUTH: targetStopY -= stopDistance; break;
        }

        switch (currentDirection) {
            case EAST: return potentialNextCell.getX() >= targetStopX;
            case WEST: return potentialNextCell.getX() <= targetStopX;
            case NORTH: return potentialNextCell.getY() <= targetStopY;
            case SOUTH: return potentialNextCell.getY() >= targetStopY;
            default: return false;
        }
    }

    public boolean update(Grid grid) {
        Cell previousCell = this.currentCell;
        Cell potentialNextCell = getNextCell(grid);

        boolean canMoveThisTick = true;
        int currentIntendedSpeed = normalSpeed;

        if (associatedTrafficLight != null) {
            if (shouldStop(potentialNextCell, associatedTrafficLight)) {
                currentIntendedSpeed = 0;
                canMoveThisTick = false;
            }
        }

        if (canMoveThisTick && currentIntendedSpeed > 0 && potentialNextCell != null) {
            if (!grid.isCellEmpty(potentialNextCell)) {
                if (grid.getCellOccupant(potentialNextCell) != this) {
                    currentIntendedSpeed = 0;
                    canMoveThisTick = false;
                }
            }
        }

        this.speed = currentIntendedSpeed;

        if (canMoveThisTick && this.speed > 0 && potentialNextCell != null) {
            if (previousCell != null) {
                grid.setCellOccupant(previousCell, null);
            }
            this.currentCell = potentialNextCell;
            grid.setCellOccupant(this.currentCell, this);
            return true;
        } else if (potentialNextCell == null && this.speed > 0) {
            if (previousCell != null) {
                grid.setCellOccupant(previousCell, null);
            }
            this.currentCell = null;
            return false;
        } else {
            return true;
        }
    }

    public Cell getNextCell(Grid grid) {
        if (currentCell == null) return null;

        int nextX = currentCell.getX();
        int nextY = currentCell.getY();

        switch (currentDirection) {
            case NORTH: nextY -= speed; break;
            case SOUTH: nextY += speed; break;
            case EAST: nextX += speed; break;
            case WEST: nextX -= speed; break;
        }

        if (nextX >= 0 && nextX < grid.getCols() && nextY >= 0 && nextY < grid.getRows()) {
            return grid.getCell(nextX, nextY);
        }
        return null;
    }

    public char getConsoleChar() {
        switch(currentDirection) {
            case NORTH: return '^';
            case SOUTH: return 'v';
            case EAST:  return '>';
            case WEST:  return '<';
            default: return 'C'; // Default car representation
        }
    }

    public Cell getCurrentCell(){ return currentCell; }
    public Direction getCurrentDirection(){ return currentDirection; }
    public int getSpeed(){ return speed; }
}
