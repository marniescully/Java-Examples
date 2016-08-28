package cscie55.hw3;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marnie Scully
 * @version 1.0 for use in Homework # 3
 * @since April 24, 2015 <br>
 */

public class Building {

    /** Class Constants * */
    public static final int FLOORS = 7;
    public static final int UNDEFINED_FLOOR = -1;
    public static final int GROUND_FLOOR = 1;

    /** Data Model * */
    private Elevator elevator;
    private List<Floor> floorsList = new ArrayList<Floor>();

    /** Constructor */
    public Building() {
        /** Creates a new Elevator object */
        elevator = new Elevator(this);

        //initialize the ArrayList of floors
        for (int i = 0; i < FLOORS + 1 ; i++) {
            Floor floor = new Floor(this, i );
            this.floorsList.add(floor);
        }
    }

    /**
     * A public getter method to get the value of the elevator
     *
     * @return elevator
     */
    public Elevator elevator(){
        return elevator;
    }

    /**
     * A public method
     * given the
     * @param floorNumber the floor number
     *
     * @return the Floor instance at that index of the ArrayList
     */
    public Floor floor(int floorNumber){
        return floorsList.get(floorNumber);
    }

    /**
     * A public method that takes a passenger instance and adds it to
     * the GROUND_FLOOR 's residents
     *
     * @param passenger a passenger instance
     */
    public void enter(Passenger passenger) {
        floorsList.get(GROUND_FLOOR).enterGroundFloor(passenger);
    }
}
