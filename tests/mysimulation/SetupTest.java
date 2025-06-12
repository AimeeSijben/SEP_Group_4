package mysimulation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class SetupTest {

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

    @Test
    void corectboardupload(){
        List<String> inputLines = new ArrayList<>();
        inputLines.add("E E E ↑ E E");
        inputLines.add("E E E ↑ E E");
        inputLines.add("S → → X → →");
        inputLines.add("E E E ↑ E E");
        inputLines.add("E E E ↑ E E");
        inputLines.add("E E E S E E");
        Grid grid = new Grid(0, 0);
        grid.loadFromText(inputLines);

        for (int r = 0; r < 6; r++) {
            String[] rowSymbols = inputLines.get(r).split(" ");
            for (int c = 0; c < 6; c++) {
                String symbol = rowSymbols[c];
                Cell.Type expected = charToCellType(symbol);
                Cell.Type actual = grid.getCell(r, c).getBoardType();
                assertEquals(expected, actual, "Mismatch at (" + r + "," + c + ")");
            }
        }
    }

    @Test
    void placeTraficlight(){
        List<String> inputLines = new ArrayList<>();
        inputLines.add("E E E ↑ E E");
        inputLines.add("E E E ↑ E E");
        inputLines.add("S → → X → →");
        inputLines.add("E E E ↑ E E");
        inputLines.add("E E E ↑ E E");
        inputLines.add("E E E S E E");
        Grid grid = new Grid(0, 0);
        grid.loadFromText(inputLines);

        assertEquals(grid.getWestCell(grid.intersections.row, grid.intersections.col).getType() ,Cell.Type.TRAFICLIGHT);
        assertEquals(grid.getWestCell(grid.intersections.row, grid.intersections.col).getType() ,Cell.Type.TRAFICLIGHT);
    }

    @Test
    void spawnCarAtSpwanPoint(){
        List<String> inputLines = new ArrayList<>();
        inputLines.add("E E E ↑ E E");
        inputLines.add("E E E ↑ E E");
        inputLines.add("S → → X → →");
        inputLines.add("E E E ↑ E E");
        inputLines.add("E E E ↑ E E");
        inputLines.add("E E E S E E");
        Grid grid = new Grid(0, 0);
        grid.loadFromText(inputLines);

        List<Position> spawns = grid.getSpawns();
        grid.spawnCar();

        boolean carSpawned = false;
        for (int i = 0; i < spawns.size(); i++) {
            Position pos = spawns.get(i);
            Cell cell = grid.getCell(pos.row, pos.col);
            if(cell.getType() == Cell.Type.CAR){
                carSpawned = true;
            }
        }
        assertTrue(carSpawned, "No car was spawned at any spawn point!");
    }

    @Test
    void carDespawnsAtBoarder(){
        List<String> inputLines = new ArrayList<>();
        inputLines.add("E E E ↑ E E");
        inputLines.add("E E E ↑ E E");
        inputLines.add("S → → X → →");
        inputLines.add("E E E ↑ E E");
        inputLines.add("E E E ↑ E E");
        inputLines.add("E E E S E E");
        Grid grid = new Grid(0, 0);
        grid.loadFromText(inputLines);

        Car car0 = new Car(0,Direction.EAST);

        grid.getCell(2,5).setCar(car0);// set car at boarder

        grid.updateCars();
        assertNull(grid.getCell(2, 5).getCar(), "at the border did not despawn");
    }

    @Test
    void carsCanMove(){
        List<String> inputLines = new ArrayList<>();
        inputLines.add("E E E ↑ E E");
        inputLines.add("E E E ↑ E E");
        inputLines.add("S → → X → →");
        inputLines.add("E E E ↑ E E");
        inputLines.add("E E E ↑ E E");
        inputLines.add("E E E S E E");
        Grid grid = new Grid(0, 0);
        grid.loadFromText(inputLines);

        Car car0 = new Car(0,Direction.EAST);
        Car car1 = new Car(1,Direction.NORTH);

        grid.getCell(2,1).setCar(car0);
        grid.getCell(1,3).setCar(car1);

        grid.updateCars();

        assertEquals(car0.getId(), grid.getCell(2,2).getCar().getId(), "car0 did not move");
        assertEquals(car0.getId(), grid.getCell(2,3).getCar().getId(), "car1 did not move");
    }





}