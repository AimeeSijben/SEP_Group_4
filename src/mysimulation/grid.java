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
        placeTraficlight();
    }

    private void placeTraficlight(){
        // Check from UP
        if (grid[intersections.row - 1][intersections.col].getType() == cell.Type.ROADDOWN) {
            grid[intersections.row - 1][intersections.col].setTrafficLight(new TrafficLight(main.clockTime, Direction.NORTH));
        }

        // Check from DOWN
        if (grid[intersections.row + 1][intersections.col].getType() == cell.Type.ROADUP) {
            grid[intersections.row + 1][intersections.col].setTrafficLight(new TrafficLight(main.clockTime, Direction.SOUTH));
        }

        // Check from LEFT
        if (grid[intersections.row][intersections.col - 1].getType() == cell.Type.ROADRIGHT) {
            grid[intersections.row][intersections.col - 1].setTrafficLight(new TrafficLight(main.clockTime, Direction.WEST));
        }

        // Check from RIGHT
        if (grid[intersections.row][intersections.col + 1].getType() == cell.Type.ROADLEFT) {
            grid[intersections.row][intersections.col + 1].setTrafficLight(new TrafficLight(main.clockTime, Direction.EAST));
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
        Car Car = new Car(main.clockTime);
        grid[spawn.row][spawn.col].setCar(Car);
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
