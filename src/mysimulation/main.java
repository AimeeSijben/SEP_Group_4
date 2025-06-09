package mysimulation;

import java.util.Scanner;

public class main {
    private static int clockTime = 2000;
    private volatile boolean paused = false;
    private volatile boolean running = true;

    private void Commands(Scanner commands){
        while(true){
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


    public void Simulate(){

        Scanner scanner = new Scanner(System.in);
        Thread commandThread = new Thread(() -> Commands(scanner));
        commandThread.setDaemon(true);
        commandThread.start();

        while (running) {
            if (!paused){
                /*
                loop(trafic lights){
                    if( color =green ){
                        remove care from que at stop light
                        add that care to moving
                    } esle{
                    do nothing
                    }
                } loop is for later if we want more intersections
                from vihevile add new vihecile
                */
            }else{
                System.out.println("Paused.\n");
            }
            try {
                Thread.sleep(clockTime); // 1000 milliseconds = 1 second
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void main(String[] args) {
        main simulation = new main();
        simulation.Simulate();
    }
}