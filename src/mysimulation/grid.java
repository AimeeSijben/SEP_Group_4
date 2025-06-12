package mysimulation;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
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
                Cell cell = new Cell();
                cell.setType(charToCellType(symbols[c]));
                Grid[r][c] = cell;
                if (cell.getType() == mysimulation.Cell.Type.SPAWN) {
                    spawns.add(new Position(r, c));
                } else if (cell.getType() == mysimulation.Cell.Type.INTERSECTION) {
                    intersections = new Position(r, c);
                }
            }
        }
        if (intersections != null) {
            placeTraficlight();
        }
    }

    public void placeTraficlight() {
        int row = intersections.row;
        int col = intersections.col;
        Map<Direction, mysimulation.Cell.Type> surrounding = getSurroundingTypes(intersections);
        for (Map.Entry<Direction, mysimulation.Cell.Type> entry : surrounding.entrySet()) {
            Direction direction = entry.getKey();
            mysimulation.Cell.Type type = entry.getValue();

            // Check from UP
            if (type == Cell.Type.ROADUP && direction == Direction.NORTH) {
                Grid[row + 1][col].setTrafficLight(new TrafficLight(Main.clockTime, direction));
            }

            if (type == Cell.Type.ROADDOWN && direction == Direction.SOUTH) {
                Grid[row - 1][col].setTrafficLight(new TrafficLight(Main.clockTime, direction));
            }

            if (type == Cell.Type.ROADLEFT && direction == Direction.WEST) {
                Grid[row][col + 1].setTrafficLight(new TrafficLight(Main.clockTime, direction));
            }

            if (type == Cell.Type.ROADRIGHT && direction == Direction.EAST) {
                Grid[row][col - 1].setTrafficLight(new TrafficLight(Main.clockTime, direction));
            }
        }
    }

    private mysimulation.Cell.Type charToCellType(String symbol) {
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
        Map<Direction, mysimulation.Cell.Type> surrounding = getSurroundingTypes(spawn);
        for (Map.Entry<Direction, mysimulation.Cell.Type> entry : surrounding.entrySet()) {
            Direction direction = entry.getKey();
            mysimulation.Cell.Type type = entry.getValue();
            if (type == Cell.Type.CAR || type == Cell.Type.ROADDOWN || type == Cell.Type.ROADUP || type == Cell.Type.ROADLEFT || type == Cell.Type.ROADRIGHT) {
                return new Car(Main.clockTime, direction);
            }
        }

        // not a valid spawn point
        return null;
    }

    private Map<Direction, mysimulation.Cell.Type> getSurroundingTypes(Position pos) {
        Map<Direction, mysimulation.Cell.Type> surrounding = new HashMap<>();

        int row = pos.row;
        int col = pos.col;

        if (row > 0) {
            surrounding.put(Direction.NORTH, Grid[row - 1][col].getType());
        }
        if (row < rows - 1) {
            surrounding.put(Direction.SOUTH, Grid[row + 1][col].getType());
        }
        if (col > 0) {
            surrounding.put(Direction.WEST, Grid[row][col - 1].getType());
        }
        if (col < collums - 1) {
            surrounding.put(Direction.EAST, Grid[row][col + 1].getType());
        }

        return surrounding;
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


    public void updateCars() {
        List<Long> moved = new ArrayList<>();

        Map<Cell, Position> cars = carPositions();
        for (Map.Entry<Cell, Position> entry : cars.entrySet()) {
            Cell cell = entry.getKey();
            Position position = entry.getValue();

            Car car = cell.getCar();

            Position delta = car.getdir().move();  // get direction vector
            int newRow = position.row + delta.row;
            int newCol = position.col + delta.col;

            if (newRow >= 0 && newRow < rows &&
                    newCol >= 0 && newCol < collums &&
                    this.Grid[newRow][newCol].getType() != Cell.Type.EMPTY) {
                if (  Grid[newRow][newCol].getCar() != null && moved.contains(Grid[newRow][newCol].getCar().getId()) ) { // is there a moved  car infront
                    continue;
                } else if (Grid[newRow][newCol].isCarAtTraficlight()) { // is the car at a traficlight
                    //add to que from road
                } else {
                    this.Grid[newRow][newCol].setCar(car);
                    moved.add(car.getId());
                }
            }
            cell.setCar(null);
        }
    }


    private Map<Cell, Position> carPositions() {
        Map<Cell, Position> cars = new HashMap<>();
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.collums; c++) {
                Car car = this.Grid[r][c].getCar();
                if (car != null) {
                    cars.put(this.Grid[r][c], new Position(r, c));
                }
            }
        }
        return cars;
    }


    public void printGrid (){
        for (Cell[] row : Grid) {
            for (Cell cell : row) {

                System.out.print(cell.toString() + " ");
            }
            System.out.println();
        }
    }

    public List<String> inputLines() {
        List<String> inputLines = new ArrayList<>();

        for (Cell[] row : Grid) {
            StringBuilder line = new StringBuilder();
            for (Cell cell : row) {
                line.append(cell.toString()).append(" ");
            }
            inputLines.add(line.toString().trim());
        }

        return inputLines;
    }

    public List<Position> getSpawns(){
        return spawns;
    }



}
