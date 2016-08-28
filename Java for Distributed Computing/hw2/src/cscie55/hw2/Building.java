package cscie55.hw2;

/**
 * @author Marnie Scully
 * @version 2.0 for use in Homework # 2
 * @since March 25, 2015 <br>
 */

public class Building {

    public static final int FLOORS = 7;
    protected Elevator elevator;
    protected int[] waitingFloors = new int[Building.FLOORS];

    public Building() {
        /** Creates a new Elevator object */
        elevator = new Elevator(this);
        for (int i = 1; i < Building.FLOORS; i++) {
            waitingFloors[i] = this.floor(i).passengersWaiting();
        }
    }

    public Elevator elevator(){
        return elevator;
    }

    public Floor floor(int floorNumber){
        return new Floor(this, floorNumber);
    }


}
