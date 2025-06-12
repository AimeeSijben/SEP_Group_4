# Report for Project 1
## SEP_Group_4

## Project

Description: A simulation of a intersection of two roads with a traffic light, where the traffic light controls the flow of cars through traffic

Programming Language: Java

## Initial Tests

### Vincent Initial Test
Tested Road.java, created spawnCars_WhenArrivalRateIsOne_ShouldEnqueueCarWithCorrectTimestamp, which tests if spawnCars enqueues a new car with the correct time stamp. Coverage was calculated using Jacoco, a tool that works with gradle to produce HTML files with coverage results. Coverage was less than 1%.
![](.\out\plantuml\Test-Coverage-Results\OneRoadTest.png)


### Aimee Initial Test
Tested Car.java, and Road.java. Created testUniqueId, which tests if Car generated a unique id for every car in the queues. Coverage was 3%. 

### lucas Initial Test
Tested Grid.java and Cell.Java with corectboardupload. witch tests if the board is taken corectly in to grid with the corect cells. 
Coverage was:26%
![image](https://github.com/user-attachments/assets/73da38af-dd90-4cd7-a874-3a6549500b2f)
### Bruno Initial Test
Initial Coverage: My first test, isConflict_PerpendicularWithDivisibleCycles_ReturnsTrue, gave me a baseline 3% coverage. This percentage is low, but it reflects the low amount of tests that were used (1).
![](.\out\plantuml\testcoverage\isConflict_PerpendicularWithDivisibleCycles_ReturnsTrue.jpg)


# Coverage Improvement

### Vincent coverage improvement
https://github.com/AimeeSijben/SEP_Group_4/commit/d0473814d3017ae5ebd90c05dd55dda85c8edacd
https://github.com/AimeeSijben/SEP_Group_4/commit/2a5f81d4791af26d485d1995db64a29e5573f7b4 
In each commit, I added a new test to RoadTest.java. By adding these two tests, the coverage of the entire program increased from less than 1% to 6%
![](.\out\plantuml\Test-Coverage-Results\ThreeRoadTests.png)

### Aimee coverage improvement
I added testCarDequeued, which tests if a car is dequeued when the light is green, and created testCarNotDequeued which tests if a car is not dequeued when the light is red. Also created testQueueOrderPreserved which tests if the que actually decreases when a car is served. This increased the coverage of the entire program from 3% to 14%.

### lucas coverage improvement
i added placeTraficlight witch test if the Traficlight i placed corectly at the intersection. i added spawnCarAtSpwanPoint witch checks if a car is spawned on a spawn block. i added carDespawnsAtBoarder witch tests if the car is at the boarder and moves will it get off the board. i added carsCanMove witch checks if the car moves by following the rode infront one cpace at a time.
witch gave me a 45% coverage:
![image](https://github.com/user-attachments/assets/9218c1d0-f364-49dc-8bef-6b9479323974)

### Bruno Coverage Improvement
Improving Coverage: By adding two more tests, isPerpendicular_VariousDirections_ReturnsCorrectValue and update_TimerAtHalfCycle_ChangesToGreen, and combining them with my initial conflict test, I increased my overall coverage to 12%. This directly reflects the assignment's goal of writing more tests to improve coverage and then measuring that improvement. I have written more tests that do not seem relevant in this phase as they are obvious in their functionality eg the max function should always return the maximum given that it builds upon very basic atomic instructions.
![](.\out\plantuml\testcoverage\3tests.jpg)
Diagrams
I created 6 diagrams which help to visually illustrate the inner workings of the system. They have been created using plantuml which allows for dynamic compilation of said diagrams




| Member | Three functions (names with links to the code on the repository) created | Initial test (name) | Other tests (names) |
| --- | --- | --- | --- |
| Vincent Wren-Larocca | [Functions within Road.java](https://github.com/AimeeSijben/SEP_Group_4/blob/main/src/mysimulation/Road.java) | spawnCars_WhenArrivalRateIsOne_ShouldEnqueueCarWithCorrectTimestamp| spawnCars_WhenArrivalRateIsZero_ShouldNotEnqueueAnyCars, serve_WhenDirectionNegative_ShouldServeOnlyNegativeLane|
| Aimée Sijben | Functions within Car.java (https://github.com/AimeeSijben/SEP_Group_4/blob/main/src/mysimulation/Car.java)| | |
| Lucas van krevel | worked on Functions within Grid.java (https://github.com/AimeeSijben/SEP_Group_4/blob/main/src/mysimulation/grid.java) and worked on Functions within Cell.java (https://github.com/AimeeSijben/SEP_Group_4/blob/main/src/mysimulation/Cell.java) | corectboardupload, placeTraficlight, spawnCarAtSpwanPoint| carDespawnsAtBoarder, carsCanMove |
| Member Bruno| Functions within TrafficLight.java (https://github.com/AimeeSijben/SEP_Group_4/blob/main/src/mysimulation/TrafficLight.java)|isConflict,isPerpendicular,update,max,min,iseven,setState,getState,TrafficLight | |



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

    Car spawns at a random Spawn point with a direction.

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


Test Coverage
The first initial test was isConflict_PerpendicularWithDivisibleCycles_ReturnsTrue, it aimed to test if valid traffic lights would return a valid state back. It provided a baseline test coverage of 3%. 
![](.\out\plantuml\testcoverage\isConflict_PerpendicularWithDivisibleCycles_ReturnsTrue.jpg)

Combining the three tests isPerpendicular_VariousDirections_ReturnsCorrectValue, update_TimerAtHalfCycle_ChangesToGreenall and the initial isConflict_PerpendicularWithDivisibleCycles_ReturnsTrue test we go a coverage of 12%.
![](.\out\plantuml\testcoverage\3tests.jpg)



