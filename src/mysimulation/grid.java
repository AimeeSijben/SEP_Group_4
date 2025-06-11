package mysimulation;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Grid {
    private Cell[][] Grid;
    private int rows = 0;
    private int collums = 0;
    private List<Position> spawns = new ArrayList<>();
    public Position intersections = new Position(0, 0);

    public Grid(int rows, int cols) {
        Grid = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Grid[r][c] = new Cell();
            }
        }
    }

    public void loadFromText(List<String> lines) {
        this.rows = lines.size();
        this.collums = lines.get(0).split(" ").length;
        Grid = new Cell[rows][collums];

        for (int r = 0; r < rows; r++) {
            String[] symbols = lines.get(r).split(" ");
            for (int c = 0; c < collums; c++) {
                Cell Cell = new Cell();
                Cell.setType(charToCellType(symbols[c]));
                Grid[r][c] = Cell;
                if (Cell.getType() == mysimulation.Cell.Type.SPAWN) {
                    spawns.add(new Position(r, c));
                } else if (Cell.getType() == mysimulation.Cell.Type.INTERSECTION) {
                    intersections = new Position(r, c);
                }
            }
        }
        if (intersections != null) {
            placeTraficlight();
        }
    }

    public Direction typeToDirection(Cell.Type type) {
        return switch (type) {
            case ROADUP -> Direction.NORTH;
            case ROADDOWN -> Direction.SOUTH;
            case ROADLEFT -> Direction.WEST;
            case ROADRIGHT -> Direction.EAST;
            default -> null;
        };
    }

    private void placeTraficlight() {
        int row = intersections.row;
        int col = intersections.col;

        // Check from UP
        if (getNorthCell(row, col).getType() == Cell.Type.ROADDOWN) {
            getNorthCell(row, col).setTrafficLight(new TrafficLight(Main.clockTime, Direction.NORTH));
        }

        // Check from DOWN
        if (getSouthCell(row, col).getType() == Cell.Type.ROADUP) {
            getSouthCell(row, col).setTrafficLight(new TrafficLight(Main.clockTime, Direction.SOUTH));
        }

        // Check from LEFT
        if (getWestCell(row, col).getType() == Cell.Type.ROADRIGHT) {
            getWestCell(row, col).setTrafficLight(new TrafficLight(Main.clockTime, Direction.WEST));
        }

        // Check from RIGHT
        if (getEastCell(row, col).getType() == Cell.Type.ROADLEFT) {
            getEastCell(row, col).setTrafficLight(new TrafficLight(Main.clockTime, Direction.EAST));
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
        Position spawn = spawns.get(randomSpan);
        Car Car = setCar(spawn);
        Grid[spawn.row][spawn.col].setCar(Car);
    }

    private Car setCar(Position spawn) {
        int row = spawn.row;
        int col = spawn.col;

        // Check from UP
        if (getNorthCell(row, col).getType() == Cell.Type.ROADUP) {
            return new Car(Main.clockTime, Direction.NORTH);
        }

        // Check from DOWN
        if (getSouthCell(row, col).getType() == Cell.Type.ROADDOWN) {
            return new Car(Main.clockTime, Direction.SOUTH);
        }

        // Check from LEFT
        if (getWestCell(row, col).getType() == Cell.Type.ROADLEFT) {
            return new Car(Main.clockTime, Direction.WEST);
        }

        // Check from RIGHT
        if (getEastCell(row, col).getType() == Cell.Type.ROADRIGHT) {
            return new Car(Main.clockTime, Direction.EAST);
        }

        // No incoming road found
        return null;
    }

    public Cell getCell(int r, int c) {
        if (r + 1 > this.rows || c + 1 > this.collums) {
            return new Cell();
        }
        return this.Grid[r][c];
    }

    public Cell getNorthCell(int r, int c) {
        r += Direction.NORTH.moveRow();
        c += Direction.NORTH.moveCol();

        if (r + 1 > this.rows || c + 1 > this.collums) {
            return new Cell();
        }
        return this.Grid[r][c];
    }

    public Cell getEastCell(int r, int c) {
        r += Direction.EAST.moveRow();
        c += Direction.EAST.moveCol();

        if (r + 1 > this.rows || c + 1 > this.collums) {
            return new Cell();
        }
        return this.Grid[r][c];
    }

    public Cell getSouthCell(int r, int c) {
        r += Direction.SOUTH.moveRow();
        c += Direction.SOUTH.moveCol();

        if (r + 1 > this.rows || c + 1 > this.collums) {
            return new Cell();
        }
        return this.Grid[r][c];
    }

    public Cell getWestCell(int r, int c) {
        r += Direction.WEST.moveRow();
        c += Direction.WEST.moveCol();

        if (r + 1 > this.rows || c + 1 > this.collums) {
            return new Cell();
        }
        return this.Grid[r][c];
    }

    public void moveCar() {
        List<Long> moved = new ArrayList<>();

        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.collums; c++) {
                Car car = this.Grid[r][c].getCar();

                // only move if car exists and hasn't moved yet
                if (car != null && !moved.contains(car.getId())) {
                    Position delta = car.getdir().move();
                    int newRow = r + delta.row;
                    int newCol = c + delta.col;

                    // Check if destination is valid
                    if (newRow >= 0 && newRow < rows &&
                            newCol >= 0 && newCol < collums &&
                            this.Grid[newRow][newCol].getCar() == null &&
                            this.Grid[newRow][newCol].getType() != Cell.Type.EMPTY) {

                        this.Grid[newRow][newCol].setCar(car);
                        moved.add(car.getId());
                    }
                    this.Grid[r][c].setCar(null);
                }
            }
        }
    }

    public void printGrid() {
        for (Cell[] row : Grid) {
            for (Cell Cell : row) {

                System.out.print(Cell.toString() + " ");
            }
            System.out.println();
        }
    }

}
