package mysimulation;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class grid {
    private cell[][] grid;
    private int rows = 0;
    private int collums = 0;
    private List<Position> spawns = new ArrayList<>();
    public Position intersections = new Position(0,0);

    public grid(int rows, int cols) {
        grid = new cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new cell();
            }
        }
    }

    public void loadFromText(List<String> lines) {
        this.rows = lines.size();
        this.collums = lines.get(0).split(" ").length;
        grid = new cell[rows][collums];

        for (int r = 0; r < rows; r++) {
            String[] symbols = lines.get(r).split(" ");
            for (int c = 0; c < collums; c++) {
                cell cell = new cell();
                cell.setType(charToCellType(symbols[c]));
                grid[r][c] = cell;
                if(cell.getType() == mysimulation.cell.Type.SPAWN){
                    spawns.add(new Position(r, c));
                }else if(cell.getType() == mysimulation.cell.Type.INTERSECTION){
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
        if (row > 0 && grid[row - 1][col].getType() == cell.Type.ROADDOWN) {
            grid[row - 1][col].setTrafficLight(new TrafficLight(main.clockTime, Direction.NORTH));
        }

        // Check from DOWN
        if (row < rows - 1 && grid[row + 1][col].getType() == cell.Type.ROADUP) {
            grid[row + 1][col].setTrafficLight(new TrafficLight(main.clockTime, Direction.SOUTH));
        }

        // Check from LEFT
        if (col > 0 && grid[row][col - 1].getType() == cell.Type.ROADRIGHT) {
            grid[row][col - 1].setTrafficLight(new TrafficLight(main.clockTime, Direction.WEST));
        }

        // Check from RIGHT
        if (col < collums - 1 && grid[row][col + 1].getType() == cell.Type.ROADLEFT) {
            grid[row][col + 1].setTrafficLight(new TrafficLight(main.clockTime, Direction.EAST));
        }
    }

    private cell.Type charToCellType(String symbol) {
        return switch (symbol) {
            case "↑" -> cell.Type.ROADUP;
            case "↓" -> cell.Type.ROADDOWN;
            case "←" -> cell.Type.ROADLEFT;
            case "→" -> cell.Type.ROADRIGHT;
            case "E" -> cell.Type.EMPTY;
            case "S" -> cell.Type.SPAWN;
            case "X" -> cell.Type.INTERSECTION;
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
        if (row > 0 && grid[row - 1][col].getType() == cell.Type.ROADUP) {
            return new Car(main.clockTime, Direction.NORTH);
        }

        // Check from DOWN
        if (row < rows - 1 && grid[row + 1][col].getType() == cell.Type.ROADDOWN) {
            return new Car(main.clockTime, Direction.SOUTH);
        }

        // Check from LEFT
        if (col > 0 && grid[row][col - 1].getType() == cell.Type.ROADLEFT) {
            return new Car(main.clockTime, Direction.WEST);
        }

        // Check from RIGHT
        if (col < collums - 1 && grid[row][col + 1].getType() == cell.Type.ROADRIGHT) {
            return new Car(main.clockTime, Direction.EAST);
        }

        // No incoming road found
        return null;
    }

    public void moveCar() {
        List<Position> moved = new ArrayList<>();

        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.collums; c++) {
                Car car = this.grid[r][c].getcar();

                // Check manually if (r, c) is already in the moved list
                boolean alreadyMoved = false;
                for (Position p : moved) {
                    if (p.row == r && p.col == c) {
                        alreadyMoved = true;
                        break;
                    }
                }

                if (car != null && !alreadyMoved) {
                    Position delta = car.getdir().move();
                    int newRow = r + delta.row;
                    int newCol = c + delta.col;

                    // Check if new position is within bounds and valid
                    if (newRow >= 0 && newRow < rows &&
                            newCol >= 0 && newCol < collums &&
                            this.grid[newRow][newCol].getcar() == null &&
                            this.grid[newRow][newCol].getType() != cell.Type.EMPTY) {

                        this.grid[newRow][newCol].setCar(car);
                        moved.add(new Position(newRow, newCol));
                    }
                    this.grid[r][c].setCar(null);
                }
            }
        }
    }

    public void printGrid() {
        for (cell[] row : grid) {
            for (cell cell : row) {

                System.out.print(cell.toString() + " ");
            }
            System.out.println();
        }
    }


}
