package mysimulation;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

//import static mysimulation.Grid.*;

public class Main {
    public static int clockTime = 1;
    private volatile boolean paused = false;
    private volatile boolean running = true;

    private void Commands(Scanner commands) {
        while (true) {
            String input = commands.nextLine().trim().toLowerCase();
            switch (input) {
                case "start":
                    paused = false;
                    System.out.println("continue.\n");
                    break;
                case "pause":
                    paused = true;
                    break;
                case "exit":
                    running = false;
                    System.out.println("exit.\n");
                    break;
            }
        }
    }

    private void Simulate(Scanner scanner, Grid gird) {

        Thread commandThread = new Thread(() -> Commands(scanner));
        commandThread.setDaemon(true);
        commandThread.start();

        while (running) {
            if (!paused) {
                gird.spawnCar();
                /*
                 * loop(trafic lights){
                 * if( color =green ){
                 * remove care from que at stop light
                 * add that care to moving
                 * } esle{
                 * do nothing
                 * }
                 * } loop is for later if we want more intersections
                 * from vihevile add new vihecile
                 */
                gird.moveCar();
                gird.printGrid();
                System.out.println("\n");
            } else {
                System.out.println("Paused.\n");
            }
            clockTime++;
            try {
                Thread.sleep(3000); // 1000 milliseconds = 1 second
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void getroad(Scanner scanner, Grid Grid) {
        List<String> inputLines = new ArrayList<>();

        System.out.println("Enter Grid rows (use symbols like ↓ → ← ↑ E), one row per line.");
        System.out.println("Type END to finish:");

        while (true) {
            String line = scanner.nextLine();
            if (line.trim().equals("end")) {
                break;
            }
            inputLines.add(line);
        }

        // assumes you have a no-arg constructor
        Grid.loadFromText(inputLines);
        Grid.printGrid();
        System.out.println("\n");
    }

    public static void Main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Main simulation = new Main();
        Grid Grid = new Grid(0, 0);
        simulation.getroad(scanner, Grid);
        simulation.Simulate(scanner, Grid);
    }
}
