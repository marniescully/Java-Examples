package cscie55.hw3;

import java.util.Comparator;

/**
 * @author Marnie Scully
 * @version 1.0 for use in Homework # 3
 * @since April 24, 2015 <br>
 */
public class Passenger  {

    /** Default values for currentFloor and destinationFloor */
    private int currentFloor = 1;
    private int destinationFloor = Building.UNDEFINED_FLOOR;
    private int passengerID = 0;

    /** Creates a new Passenger object
     * @param passengerID the ID # of a passenger*/
    public Passenger(int passengerID) {
        this.passengerID = passengerID;
    }

    /** Comparator for sorting the passengers by ID# */
    public static Comparator<Passenger> passIDCompare = new Comparator<Passenger>() {
        public int compare(Passenger p1, Passenger p2) {
            int no1 = p1.passengerID();
            int no2 = p2.passengerID();
            return no1-no2;
        }
    };

    /**
     * A public getter method to get the value of the currentFloor
     *
     * @return currentFloor
     */
    public int currentFloor() {
         return currentFloor;
     }

    /**
     * A public getter method to get the value of the passengerID
     *
     * @return passengerID
     */
    public int passengerID() {
        return passengerID;
    }

    /**
     * A public getter method to get the value of the destinationFloor
     *
     * @return destinationFloor
     */
    public int destinationFloor() {
        return destinationFloor;
    }

        /**
     * A public setter method to set the value of the instance variable destinationFloor
     *
     * @param newDestinationFloor takes parameter value and sets the value of destinationFloor variable
     */
    public void waitForElevator(int newDestinationFloor) {
        this.destinationFloor = newDestinationFloor;
    }

    /**
     * A public setter method to set the value of the instance variable currentFloor to UNDEFINED_FLOOR as
     * Passenger boards the elevator
     */
    public void boardElevator() {
        this.currentFloor = Building.UNDEFINED_FLOOR;
    }

    /**
     * A public method to set the value of the instance variables currentFloor and destinationFloor
     *  as Passenger leaves the elevator, the currentFloor takes the value of destinationFloor and destinationFloor
     *  becomes UNDEFINED_FLOOR
     */
    public void arrive() {
        this.currentFloor = this.destinationFloor;
        this.destinationFloor = Building.UNDEFINED_FLOOR;
    }

    /**
     * @return String about the currentFloor and the destinationFloor of the Passenger
     */
    public String toString() {
        return "The current floor is: " + currentFloor + " and the destination floor is:  "
                + destinationFloor + " and my passenger id is " + passengerID ;
    }

}
