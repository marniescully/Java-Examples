package cscie55.hw2;

/**
 * Created by marnie on 3/16/15.
 * Represents one floor within a building
 */
public class Floor {

    private int floorNumber;
    private Building building;
    private int passengersWaiting ;

    public Floor (Building building, int floorNumber){
        this.building = building;
        this.floorNumber = floorNumber;
        this.passengersWaiting = building.waitingFloors[floorNumber-1];
    }

    public int passengersWaiting() {
        return this.passengersWaiting;
    }


// Called when a passenger on the floor wants to wait for the elevator.
// Calling this should cause the elevator to stop the next time it moves to the floor.
// For this homework assume that passengers waiting for the elevator on floors 2 and
// above should all be boarded as going to the first floor.

    public void waitForElevator() {

        building.waitingFloors[floorNumber -1]++;

        this.passengersWaiting = building.waitingFloors[floorNumber-1];

        building.elevator().setFloorsWithStops(floorNumber);


    }

}
