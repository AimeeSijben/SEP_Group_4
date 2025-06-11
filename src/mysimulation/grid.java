package mysimulation;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Grid {
    private Cell[][] grid;
    private int rows = 0;
    private int collums = 0;
    private List<Position> spawns = new ArrayList<>();
    public Position intersections = new Position(0,0);

    public Grid(int rows, int cols) {
        grid = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell();
            }
        }
    }

    public void loadFromText(List<String> lines) {
        this.rows = lines.size();
        this.collums = lines.get(0).split(" ").length;
        grid = new Cell[rows][collums];

        for (int r = 0; r < rows; r++) {
            String[] symbols = lines.get(r).split(" ");
            for (int c = 0; c < collums; c++) {
                Cell cell = new Cell();
                cell.setType(charToCellType(symbols[c]));
                grid[r][c] = cell;
                if(cell.getType() == mysimulation.Cell.Type.SPAWN){
                    spawns.add(new Position(r, c));
                }else if(cell.getType() == mysimulation.Cell.Type.INTERSECTION){
                    intersections = new Position(r, c);
                }
            }
        }
        if(intersections != null) {
            placeTraficlight();
        }
    }

    private void placeTraficlight() {
        int row = intersections.row;
        int col = intersections.col;

        // Check from UP
        if (row > 0 && grid[row - 1][col].getType() == Cell.Type.ROADDOWN) {
            grid[row - 1][col].setTrafficLight(new TrafficLight(Main.clockTime, Direction.NORTH));
        }

        // Check from DOWN
        if (row < rows - 1 && grid[row + 1][col].getType() == Cell.Type.ROADUP) {
            grid[row + 1][col].setTrafficLight(new TrafficLight(Main.clockTime, Direction.SOUTH));
        }

        // Check from LEFT
        if (col > 0 && grid[row][col - 1].getType() == Cell.Type.ROADRIGHT) {
            grid[row][col - 1].setTrafficLight(new TrafficLight(Main.clockTime, Direction.WEST));
        }

        // Check from RIGHT
        if (col < collums - 1 && grid[row][col + 1].getType() == Cell.Type.ROADLEFT) {
            grid[row][col + 1].setTrafficLight(new TrafficLight(Main.clockTime, Direction.EAST));
        }
    }

    private Cell.Type charToCellType(String symbol) {
        return switch (symbol) {
            case "↑" -> Cell.Type.ROADUP;
            case "↓" -> Cell.Type.ROADDOWN;
            case "←" -> Cell.Type.ROADLEFT;
            case "→" -> Cell.Type.ROADRIGHT;
            case "E" -> Cell.Type.EMPTY;
            case "S" -> Cell.Type.SPAWN;
            case "X" -> Cell.Type.INTERSECTION;
            default -> throw new IllegalArgumentException("Unknown symbol: " + symbol);
        };
    }

    public void spawnCar(){
        Random random = new Random();
        int randomSpan = random.nextInt(spawns.size());
        Position spawn = spawns.get(randomSpan);
        Car Car = setCar(spawn);
        grid[spawn.row][spawn.col].setCar(Car);
    }

    private Car setCar(Position spawn) {
        int row = spawn.row;
        int col = spawn.col;

        // Check from UP
        if (row > 0 && grid[row - 1][col].getType() == Cell.Type.ROADUP) {
            return new Car(Main.clockTime, Direction.NORTH);
        }

        // Check from DOWN
        if (row < rows - 1 && grid[row + 1][col].getType() == Cell.Type.ROADDOWN) {
            return new Car(Main.clockTime, Direction.SOUTH);
        }

        // Check from LEFT
        if (col > 0 && grid[row][col - 1].getType() == Cell.Type.ROADLEFT) {
            return new Car(Main.clockTime, Direction.WEST);
        }

        // Check from RIGHT
        if (col < collums - 1 && grid[row][col + 1].getType() == Cell.Type.ROADRIGHT) {
            return new Car(Main.clockTime, Direction.EAST);
        }

        // No incoming road found
        return null;
    }

    public void updateGrid() {
        List<Long> moved = new ArrayList<>();

        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.collums; c++) {

                Car car = this.grid[r][c].getcar();

                // only move if car exists and hasn't moved yet
                if (car != null && !moved.contains(car.getId())) {
                    Position delta = car.getdir().move();
                    int newRow = r + delta.row;
                    int newCol = c + delta.col;

                    // Check if destination is valid
                    if (newRow >= 0 && newRow < rows &&
                            newCol >= 0 && newCol < collums &&
                            this.grid[newRow][newCol].getcar() == null &&
                            this.grid[newRow][newCol].getType() != Cell.Type.EMPTY) {

                        //check if a car is at a traffic light
                        if(grid[newRow][newCol].isCarAtTraficlight()){
                            //add to que from road
                        }

                        this.grid[newRow][newCol].setCar(car);
                        moved.add(car.getId());
                    }
                    //unmark a car's previous spot .
                    this.grid[r][c].setCar(null);

                }
                //  check for green traffic lights
                TrafficLight trafficLight = this.grid[r][c].getTrafficLight();
                if(trafficLight.getState() == TrafficLight.State.GREEN){
                    // deque cars from road
                }

            }
        }
    }

    public void printGrid() {
        for (Cell[] row : grid) {
            for (Cell cell : row) {

                System.out.print(cell.toString() + " ");
            }
            System.out.println();
        }
    }


}
