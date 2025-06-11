# Report for Project 1
## SEP_Group_4

## Project

Description: A simulation of a intersection of two roads with a traffic light, where the traffic light controls the flow of cars through traffic

Programming Language: Java

## Initial Tests

### Vincent Initial Test
Tested Road.java, created spawnCars_WhenArrivalRateIsOne_ShouldEnqueueCarWithCorrectTimestamp, which tests if spawnCars enqueues a new car with the correct time stamp. Coverage was calculated using Jacoco, a tool that works with gradle to produce HTML files with coverage results. Coverage was less than 1%.
![](.\out\plantuml\Test-Coverage-Results\OneRoadTest.png)


Add more initial tests here


# Coverage Improvement

### Vincent coverage improvement
https://github.com/AimeeSijben/SEP_Group_4/commit/d0473814d3017ae5ebd90c05dd55dda85c8edacd
https://github.com/AimeeSijben/SEP_Group_4/commit/2a5f81d4791af26d485d1995db64a29e5573f7b4 
In each commit, I added a new test to RoadTest.java. By adding these two tests, the coverage of the entire program increased from less than 1% to 6%
![](.\out\plantuml\Test-Coverage-Results\ThreeRoadTests.png)


Add more coverage improvements here




| Member | Three functions (names with links to the code on the repository) created | Initial test (name) | Other tests (names) |
| --- | --- | --- | --- |
| Vincent Wren-Larocca | [Functions within Road.java](https://github.com/AimeeSijben/SEP_Group_4/blob/main/src/mysimulation/Road.java) | spawnCars_WhenArrivalRateIsOne_ShouldEnqueueCarWithCorrectTimestamp| spawnCars_WhenArrivalRateIsZero_ShouldNotEnqueueAnyCars, serve_WhenDirectionNegative_ShouldServeOnlyNegativeLane|
| Member B | | | |
| Member C | | | |
| Member D | | | |



1. Car Movement Logic (@startuml car)

Purpose: Shows the decision-making process for car movement
Key Components:

    Checks if next cell is empty (primary condition)

    If occupied → Signals waiting to central system

    If empty → Checks for traffic light presence

        If traffic light exists → Verifies if green

            Green → Moves car

            Red → Signals waiting (using goto for flow control)

        No traffic light → Moves car directly
        Notable Features:

    Uses labels (sp_lab0, sp_lab1) for control flow

    Ends with moveCar action when all conditions are met
![](.\out\plantuml\cardiagram\car.svg)

2. Traffic Light Conflict Detection (@startuml conflict_detection)

Purpose: Illustrates collision risk at intersections
Scenario:

    Two traffic lights (NS and EW) control an intersection

    Both lights are green simultaneously

    Two cars approaching from perpendicular directions
    Conflict Conditions:

    Lights are perpendicular (NS vs EW)

    Cycle times are multiples (both 10s)

    Both green at same time → Potential collision
    Visual Elements:

    Object diagram showing real-time state

    Notes explain conflict logic
![](.\out\plantuml\conflictscenariodiagram\conflict_detection.svg)

3. Grid System Structure (@startuml grid_structure)

Purpose: Defines core system architecture
Components:

    Grid: 2D array of cells

    Cell: Contains:

        State (Direction enum: NORTH/EAST/etc.)

        Optional Car

        Optional TrafficLight

    Car: Has ID and move() capability

    TrafficLight: Toggleable state (RED/GREEN)
    Relationships:

    Grid aggregates Cells

    Cells may hold Cars/TrafficLights

    Strong typing with enums for state
![](.\out\plantuml\griddiagram\grid_structure.svg)

4. Queue Management (@startuml queue)

Purpose: Shows car queuing mechanism
Sequence:

    Car arrives → Signals Activator

    Activator adds car to Queue

    Later, car moves away → Signals Activator

    Activator removes car from Queue
    Actors:

    Central Activator System: Mediates queue operations

    Car Queue: FIFO structure managing waiting cars
![](.\out\plantuml\queuediagram\queue.svg)

5. Car Spawning (@startuml spawn_operation)

Purpose: Demonstrates car generation logic
Mechanics:

    SpawnCell (specialized Cell):

        Tracks spawnRate and lastSpawnTime

        Inherits from base Cell
        Conditions for Spawning:

    Current time exceeds last spawn + rate

    Next cell is empty
    Dependencies:

    Polls Simulation Clock for time
![](.\out\plantuml\spawn\spawn_operation.svg)

6. Traffic Light Operation (@startuml traffic_light)

Purpose: Details traffic light behavior
Features:

    States: RED/GREEN

    Key Methods:

        update(timer): Toggles state based on cycleLength

        isConflict(): Checks perpendicular/multiple cycles

        adjustTiming(): Dynamically adapts to queue size
        Design:

    Encapsulates timing logic

    Explicit state machine via enum
![](.\out\plantuml\trafficlightdiagram\traffic_light.svg)



