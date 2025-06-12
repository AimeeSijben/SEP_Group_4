package mysimulation;

import org.junit.jupiter.api.Test;

class CarTest {

	@Test
	void testUniqueId() {
		Car.resetIdCounter();
		
		Car car1 = new Car(0);
		Car car2 = new Car(1);
		Car car3 = new Car(2);
		
		//Check IDs
		if (car1.getId() == 0 && car2.getId() == 1 && car3.getId() == 2 && car1.getId() != car2.getId() && car2.getId() != car3.getId() && car1.getId() != car3.getId()){
			System.out.println("Test testUniqueId passed");
		} else {
			System.out.println("Test testUniqueId failed: Id's not unique");
			return;
		}
	}
	
	@Test
	void testCarDequeued() {
		Car.resetIdCounter();
		
		Road road = new Road("TestRoad", 1.0);
		TrafficLight light = new TrafficLight(4, Direction.NORTH);
		road.spawnCars(0);
		
		if (road.getQueueLength(Road.Direction.POSITIVE)!= 1) {
			System.out.println("Test testCarDequeued failed: car not in queue");
			return;
		}
		
		light.update(0); //sets light to green
		if (light.getState() != TrafficLight.State.GREEN) {
			System.out.println("Test testCarDequeued failed: light not green");
		}
		
		int served = road.serve(Road.Direction.POSITIVE, 1);
		if (served == 1 && road.getQueueLength(Road.Direction.POSITIVE) == 0) {
			System.out.println("Test testDequeueCar passed");
		} else {
			System.out.println("Test testDequeueCar failed. Served: " + served + ", queue: " + road.getQueueLength(Road.Direction.POSITIVE));
		}
	}
	
	@Test
	void testNotCarDequeued() {
		Car.resetIdCounter();
		
		Road road = new Road("TestRoad", 1.0);
		TrafficLight light = new TrafficLight(4, Direction.NORTH);
		road.spawnCars(0);
		
		if (road.getQueueLength(Road.Direction.POSITIVE)!= 1) {
			System.out.println("Test testNotCarDequeued failed: car not in queue");
			return;
		}
		
		light.update(0); //sets light to red
		if (light.getState() != TrafficLight.State.RED) {
			System.out.println("Test testNotCarDequeued failed: light not red");
		}
		
		int served = road.serve(Road.Direction.POSITIVE, 1); //try to serve
		if (served == 1 && road.getQueueLength(Road.Direction.POSITIVE) == 1) {
			System.out.println("Test testNotDequeueCar passed");
		} else {
			System.out.println("Test testNotDequeueCar failed. Served: " + served + ", queue: " + road.getQueueLength(Road.Direction.POSITIVE));
		}
	}
	
	@Test
	void testQueueOrderPreserved() {
		Car.resetIdCounter();
		
		Road road = new Road("TestRoad", 1.0);
		TrafficLight light = new TrafficLight(4, Direction.NORTH);
		road.spawnCars(0);
		road.spawnCars(1);
		
		if (road.getQueueLength(Road.Direction.POSITIVE) != 2) {
			System.out.println("Test testQueueOrderPreserved failed: the que doesn't contain two cars");
			return;
		}
		
		light.update(0); //sets light to green
		
		int served = road.serve(Road.Direction.POSITIVE, 1); //serves first car
		if (served != 1 || road.getQueueLength(Road.Direction.POSITIVE)!= 1 ) {
			System.out.println("Test testQueueOrderPreserved failed: first car not served");
			return;
		}
		
		served = road.serve(Road.Direction.POSITIVE, 1); //serves second car
		if (served == 1 && road.getQueueLength(Road.Direction.POSITIVE) == 0) {
			System.out.println("Test testQueueOrderPreserved passed");
		} else {
			System.out.println("Test testNotDequeueCar failed. Served: " + served + ", queue: " + road.getQueueLength(Road.Direction.POSITIVE));			
		}
	
	}

}
