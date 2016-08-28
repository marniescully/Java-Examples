package cscie55.hw2;


/**
 * @author Marnie Scully
 * @version 2.0 for use in Homework # 2
 * @since March 25, 2015 <br>
 * <br>
 * This class simulates the actions of an elevator within a building.
 * Passengers are boarded with the public method boardPassengers(int floor);
 * called from the class ElevatorTest. The public method move(); simulates
 * an elevator moving from the ground floor to the top floor releasing passengers
 * to their desired floor. A string is generated with each move to display the
 * current floor and the total number of passengers currently on the elevator.
 */

public class Elevator {
    /** Class Constants * */

    /**
     * CAPACITY is the total number of passengers allowed on the elevator
     */
    public static final int CAPACITY = 10;

    /** The Data Model */
    /** These private instance variables get updated by the public method move() and
     *  the public method boardPassenger */

    /**
     * The current floor changes as the elevator moves up or down
     */
    private int currentFloor = 1;

    /**
     * The direction the elevator is heading. True means it is heading up
     */
    private boolean isUP = true;

    /**
     * An array of passengers destined for a floor
     */
    private int[] destinationFloors = new int[Building.FLOORS];


    /**
     * An array of stops required per floor
     */
    private boolean[] floorsWithStops = new boolean[Building.FLOORS];

    /**
     * The building that contains this elevator
     */
    private Building building;

    /**
     * The total number of passengers on the elevator
     */
    private int passengers = 0;

    /**
     * Constructor Method
     * @param building links building object with elevator
     */
    public Elevator(Building building) {
        this.building = building;

        /** Initializes both arrays to have no passengers or stops */
        for (int i = 0; i < Building.FLOORS; i++) {
            destinationFloors[i] = 0;
        }
        for (int i = 0; i < Building.FLOORS; i++) {
            floorsWithStops[i] = false;
        }
    }

    /**
     * Other classes call boardPassenger to add passenger to the elevator.
     *
     * @param destinationFloorNumber The destination floor of a passenger.
     *                               @throws ElevatorFullException when a passenger attempts to board when elevator
     *                               is at CAPACITY.
     */
    public void boardPassenger(int destinationFloorNumber) throws ElevatorFullException {

        /** Add a passenger to the destination floor array.
         *  The index is the floor number 1  */
        destinationFloors[destinationFloorNumber - 1] += 1;
        /** Add a passenger to the floorsWithStops array.
         *  The index is the floor number 1  */
        floorsWithStops[destinationFloorNumber - 1] = true;
        /** Add a passenger to the totalPassengers variable.
         *  to keep track of total number of passengers on the elevator  */
        passengers += 1;
    }

    /**
     * Moves elevator and updates currentFloor and direction, and Floor Arrays
     * As the elevator advances to a floor it checks to see if any passengers
     * are destined for that floor. If so, it sets destinationFloor[currentFloor -1] = 0 and
     * floorsWithStops[currentFloor -1] = 0 to indicate no passengers or stops on that floor now.
     * The currentFloor and the passengers variables update as the elevator moves.
     * When the top floor is reached the direction variable changes isUP to "false".
     */

    public void move() {
        /** Verify that currentFloor is between 1 and the total number of floors */
        if (0 < currentFloor && currentFloor <= Building.FLOORS) {

            if (currentFloor == 1) {
                /** Once on Ground Floor direction changes */
                setUP(true);
            }
            if (currentFloor == Building.FLOORS) {
                /** Once on Top Floor direction changes */
                setUP(false);
            }

            if (isUP) {
                /** Move to next higher floor */
                currentFloor++;

            } else {
                /** Move to next lower floor */
                currentFloor--;
            }


            /** If any stops are needed, passengers leave elevator, and arrays return to default values */
            if (floorsWithStops[currentFloor - 1]) {
                passengers -= destinationFloors[currentFloor - 1];
                destinationFloors[currentFloor - 1] = 0;
                floorsWithStops[currentFloor - 1] = false;
            }

            int waiting = building.floor(currentFloor).passengersWaiting();

            while (waiting > 0 && passengers !=CAPACITY) {
                    try {
                        boardPassenger(1);
                        waiting--;
                    } catch (ElevatorFullException e) {
                        setFloorsWithStops(currentFloor - 1);
                    } finally {
                        building.waitingFloors[currentFloor - 1] = waiting;
                    }
            }
        }
    }

    /**
     * @return String about the Current Floor and the Number of Passengers
     */
    public String toString() {
        return "The elevator is at Floor: " + currentFloor + " and contains "
                + passengers + " passengers.";
    }

    /**
     * A public method to retrieve the current direction of the elevator.
     * A Boolean indicating whether the direction is UP
     *
     * @return isUP
     */
    public boolean isUP() {
        return isUP;
    }

    /**
     * A public method to set the value of the instance variable isUP
     *
     * @param isUP takes parameter value and sets isUP to either true for UP and false for DOWN
     */
    public void setUP(boolean isUP) {
        this.isUP = isUP;
    }

    /**
     * A public method to get the value of the currentFloor
     *
     * @return currentFloor
     */
    public int currentFloor() {
        return currentFloor;
    }

    /**
     * A public method to get the value of the passengers
     *
     * @return passengers
     */
    public int passengers() {
        return passengers;
    }

    /**
     * A public method to set the value of the instance variable floorsWithStops
     *
     * @param floor takes parameter value and adds a waiting person on that floor
     */
    protected void setFloorsWithStops(int floor) {
        this.floorsWithStops[floor - 1] = true;
    }

}