package mysimulation;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static int clockTime = 2000;
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

    private void Simulate(Scanner scanner) {

        Thread commandThread = new Thread(() -> Commands(scanner));
        commandThread.setDaemon(true);
        commandThread.start();

        while (running) {
            if (!paused) {
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
            } else {
                System.out.println("Paused.\n");
            }
            try {
                Thread.sleep(clockTime); // 1000 milliseconds = 1 second
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void getroad(Scanner scanner) {
        List<String> inputLines = new ArrayList<>();

        System.out.println("Enter grid rows (use symbols like ↓ → ← ↑ E), one row per line.");
        System.out.println("Type END to finish:");

        while (true) {
            String line = scanner.nextLine();
            if (line.trim().equals("end")) {
                break;
            }
            inputLines.add(line);
        }

        Grid grid = new Grid(0, 0); // assumes you have a no-arg constructor
        grid.loadFromText(inputLines);
        grid.printGrid();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Main simulation = new Main();
        simulation.getroad(scanner);
        simulation.Simulate(scanner);
    }
}
