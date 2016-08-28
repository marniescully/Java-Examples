package cscie55.hw3;

import java.util.*;


/**
 * @author Marnie Scully
 * @version 1.0 for use in Homework # 3
 * @since April 24, 2015 <br>
 * <br>
 *
 * This project simulates the actions of an elevator within a building.
 * Passengers are boarded with the public method boardPassengers(Passenger passenger);
 * The public method move(); simulates an elevator moving from the ground floor
 * to the top floor releasing passengers to their desired floor.
 */

public class Elevator {

    /**
     * CAPACITY is a class constant that is the total number of passengers allowed on the elevator
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
     * The building that contains this elevator
     */
    private Building building;

    /**
     * The direction the elevator is heading. True means it is heading up which is the initial direction
     */
    private boolean isUP = true;

    /** One Collection of all destination Floors that contain a collection
     * of passengers for that floor */
    private HashMap<Integer, ArrayList<Passenger>> dFloors = new HashMap<Integer, ArrayList<Passenger>>();

    /** One Collection of all passengers on the elevator */
    private HashSet<Passenger> passengers = new HashSet<Passenger>();


    /**
     * Constructor Method
     * @param building links building object with elevator
     */
    public Elevator(Building building) {
        this.building = building;
    }

    /**
     * A public method to get the value of the currentFloor
     * @return currentFloor
     */
    public int currentFloor() {
        return currentFloor;
    }

    /**
     * A public method to get the value of the collection of passengers on the elevator
     * @return passengers collection
     */
    public Set<Passenger> passengers() {
        //for all of the arraylists in dFloors HashMap
        for (ArrayList<Passenger> al: dFloors.values()){
            for (Passenger p: al){
                passengers.add(p);
            }
        }
        return passengers;
    }

    /**
     * @return String about the Current Floor and the Number of Passengers
     */
    public String toString() {
        return "The elevator is at Floor: " + currentFloor() + " and contains "
                + passengers() + " passengers.";
    }

    /**
     * A public method to set the value of the instance variable isUP
     * @param isUP takes parameter value and sets isUP to either true for UP and false for DOWN
     */
    public void setUP(boolean isUP) {
        this.isUP = isUP;
    }

    /**
     * A public method to retrieve the current direction of the elevator.
     * A Boolean indicating whether the direction is Up
     *
     * @return true if elevator is going up
     */
    public boolean goingUp(){
        if (isUP) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * A public method to retrieve the current direction of the elevator.
     * A Boolean indicating whether the direction is Down
     *
     * @return true if elevator is going down
     */
    public boolean goingDown() {
        if (!isUP) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * move() calls boardPassenger to add passenger to the elevator.
     * @param  passenger a passenger instance
     * @throws ElevatorFullException when a passenger attempts to board when elevator
     * is at CAPACITY.
     */
    public void boardPassenger(Passenger passenger) throws ElevatorFullException,ConcurrentModificationException {

        if(dFloors.get(passenger.destinationFloor()) == null) {
            dFloors.put(passenger.destinationFloor(), new ArrayList<Passenger>());
        }

        ArrayList<Passenger> al = dFloors.get(passenger.destinationFloor());
        al.add(passenger);
    }

    /**
     * Moves elevator
     * As the elevator advances to a floor it checks to see if any passengers
     * are destined for that floor. It then checks to see if there are passengers waiting to board and are going in the same direction.
     * Elevator will leave passengers on floors after capacity of 10 passengers is reached.
     * They will board the next time the elevator passes them going in the same direction.
     * The currentFloor, dFloors and the passengers variables update as the elevator moves.
     * When the top floor is reached the direction variable changes isUP to "false".
     */
    public void move() {

        /** Verify that currentFloor is between 1 and the total number of floors */
        if (0 < currentFloor && currentFloor <= Building.FLOORS) {

            if (goingUp()) {
                /** Move to next higher floor */
                currentFloor++;
            }

            if (goingDown()) {
                /** Move to next lower floor */
                currentFloor--;
            }

            if (currentFloor == 1) {
                /** Once on Ground Floor direction changes */
                setUP(true);
            }

            if (currentFloor == Building.FLOORS) {
                /** Once on Top Floor direction changes */
                setUP(false);
            }

            //The current floor
            Floor buildingFloor = building.floor(currentFloor());

            //Copy collections of passengers waiting on the floors and sort them by Passenger ID
            List<Passenger> upPeople =  new ArrayList<Passenger>();
            upPeople.addAll(buildingFloor.goingUp());
            Collections.sort(upPeople, Passenger.passIDCompare);

            List<Passenger> downPeople = new ArrayList<Passenger>();
            downPeople.addAll(buildingFloor.goingDown());
            Collections.sort(downPeople, Passenger.passIDCompare);

            // If there are passengers that want to get off on this floor
            if (dFloors.get(currentFloor()) != null) {
                ArrayList<Passenger> getOff = new ArrayList<Passenger>();
                getOff.addAll(dFloors.get(currentFloor()));
                ArrayList<Passenger> on = dFloors.get(currentFloor());

                //get off the elevator
                for (Passenger p : getOff) {
                    p.arrive();
                    on.remove(p);
                    passengers().remove(p);
                    buildingFloor.enterGroundFloor(p);
                }
            }

            //Only add passengers if not at CAPACITY
            if (passengers().size() < CAPACITY) {
                // Board goingUp people if you can
                if (goingUp() && buildingFloor.goingUp().size() > 0) {
                    for (Passenger p : upPeople) {
                        if (passengers().size() !=CAPACITY) {
                            try {
                                p.boardElevator();
                                boardPassenger(p);
                                buildingFloor.goingUp().remove(p);
                            } catch (ElevatorFullException ex) {
                                System.out.println("Elevator at Capacity.");
                            }
                        }
                    }
                }

                // Board going down people if you can.
                if (goingDown() && buildingFloor.goingDown().size() > 0) {
                    for (Passenger p : downPeople) {
                        if (passengers().size() !=CAPACITY) {
                            try {
                                boardPassenger(p);
                                p.boardElevator();
                                buildingFloor.goingDown().remove(p);
                            } catch (ElevatorFullException ex) {
                                System.out.println("Elevator at Capacity.");
                            }
                        }
                    }
                }
            }
        }
    }
}