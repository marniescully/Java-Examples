package cscie55.hw3;

import java.util.*;

/**
 * @author Marnie Scully
 * @version 1.0 for use in Homework # 3
 * @since April 24, 2015 <br>
 *
 */

public class Floor {

    /*** The Data Model */
    private int floorNumber;
    private Building building;
    private Set<Passenger> resident = new HashSet<Passenger>();
    private Set<Passenger> goingUp = new HashSet<Passenger>();
    private Set<Passenger> goingDown = new HashSet<Passenger>();

    /**
     * Constructor Method
     *
     * @param building links building object with floor
     * @param floorNum is current Floor's number
     */
    public Floor(Building building, int floorNum) {
        this.building = building;
        this.floorNumber = floorNum;
    }

    /**
     * A public method to add the given passenger to the Ground Floor residents
     *
     * @param passenger a Passenger instance
     */
    public void enterGroundFloor(Passenger passenger) {
        // adds a Passenger to the ground Floor's residents
        resident.add(passenger);
    }

    /**
     * A public method to add the given passenger to the collections
     * of passengers that match their intended direction
     * goingUp or goingDown
     * It also calls the waitForElevator method of the passenger class
     * to set the destination floor of the passenger
     *
     * @param passenger a Passenger instance
     * @param destinationFloor the chosen floor of the passenger
     */
    public void waitForElevator(Passenger passenger, int destinationFloor) {
        //set passenger object's' destination floor property with Passenger.waitForElevator()
        passenger.waitForElevator(destinationFloor);

        //Compare destinationFloor with currentFloor
        // if destinationFloor is greater than current floor, then Passenger is going up
        // add to going up collection
        if (passenger.destinationFloor() > floorNumber) {
            goingUp.add(passenger);
            resident.remove(passenger);
            //if destinationFloor is less than current floor, then Passenger is going down
            //add to going down collection
        } else if (passenger.destinationFloor() < floorNumber) {
            goingDown.add(passenger);
            resident.remove(passenger);
        }

    }

    /**
     * A public method to determine if the given passenger is on this floor
     * @param passenger a passenger instance
     * @return true or false
     */
    public boolean isResident(Passenger passenger) {
        //if passenger is in the resident collection return true, false otherwise
        return resident.contains(passenger);
    }

    /**
     * A public method to return the passengers in the goingUp HashSet
     *
     *@return goingUp
     */
    public Set<Passenger> goingUp(){
        return goingUp;
    }

    /**
     * A public method to return the passengers in the goingDown HashSet
     *
     *@return goingDown
     */
    public Set<Passenger> goingDown(){
        return goingDown;
    }

    /**
     * A public method to return the passengers in the resident HashSet
     *
     *@return resident
     */
    public Set<Passenger> resident(){
        return resident;
    }

    /**
     * A public method to return this floor's floor number
     *
     *@return floorNumber
     */
    public int floorNumber() {
        return floorNumber;
    }
}