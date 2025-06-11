package mysimulation;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Grid {
    private Cell[][] grid;
    private List<Position> spawns = new ArrayList<>();
    public List<Position> intersections = new ArrayList<>();

    public Grid(int rows, int cols) {
        grid = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell();
            }
        }
    }

    public void loadFromText(List<String> lines) {
        int rows = lines.size();
        int cols = lines.get(0).split(" ").length;
        grid = new Cell[rows][cols];

        for (int r = 0; r < rows; r++) {
            String[] symbols = lines.get(r).split(" ");
            for (int c = 0; c < cols; c++) {
                Cell Cell = new Cell();
                Cell.setType(charToCellType(symbols[c]));
                grid[r][c] = Cell;

                if (Cell.getType() == mysimulation.Cell.Type.SPAWN) {
                    spawns.add(new Position(r, c));
                }

                if (Cell.getType() == mysimulation.Cell.Type.INTERSECTION) {
                    intersections.add(new Position(r, c));
                }
            }
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

    public void spawnCar() {
        Random random = new Random();
        int randomSpan = random.nextInt(spawns.size());
        Car Car = new Car(Main.clockTime);
        // Car.Cars.add(Car);
    }

    public void printGrid() {
        for (Cell[] row : grid) {
            for (Cell Cell : row) {
                System.out.print(Cell.toString() + " ");
            }
            System.out.println();
        }
    }

}
