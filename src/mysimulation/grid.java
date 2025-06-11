package mysimulation;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class grid {
    private cell[][] grid;
    private List<Position> spawns = new ArrayList<>();
    public List<Position> intersections = new ArrayList<>();

    public grid(int rows, int cols) {
        grid = new cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new cell();
            }
        }
    }

    public void loadFromText(List<String> lines) {
        int rows = lines.size();
        int cols = lines.get(0).split(" ").length;
        grid = new cell[rows][cols];

        for (int r = 0; r < rows; r++) {
            String[] symbols = lines.get(r).split(" ");
            for (int c = 0; c < cols; c++) {
                cell cell = new cell();
                cell.setType(charToCellType(symbols[c]));
                grid[r][c] = cell;

                if(cell.getType() == mysimulation.cell.Type.SPAWN){
                    spawns.add(new Position(r, c));
                }

                if(cell.getType() == mysimulation.cell.Type.INTERSECTION){
                    intersections.add(new Position(r, c));
                }
            }
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
        car car = new car(main.clockTime, spawns.get(randomSpan) );
        car.cars.add(car);
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
