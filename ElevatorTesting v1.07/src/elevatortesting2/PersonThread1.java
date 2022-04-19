/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package elevatortesting2;

/**
 *
 * @author Asher
 */
public class PersonThread1 extends Thread {

    private int destinationFloor;
    private int currentFloor;
    private int state;
    private int elevatorArrivalStatus;
    private String personName;
    //State of person:
    /*
            0 - just entered the elevator waiting area
            1 - they pressed the button, waiting
            2 - they moviung up in elev.
            3 - movingg down.
            4 - arrived
    */
    
    //direction:
    /*
            1 - up
            2 - down
     */

    //Elevator Object
    ElevatorMutex m = new ElevatorMutex();

    //Person Constructor
    PersonThread1(int destinationFloor, int current, int state, ElevatorMutex m, String name) {
        this.destinationFloor = destinationFloor;
        currentFloor = current;
        this.state = state;
        personName = name;
        this.m = m;
        
        System.out.println(name + " is walking to the elevator area");
    }

    public void run() {
        int directionButton = getDirection();
        synchronized (m) {
            m.enterElevatorArea(destinationFloor, currentFloor, personName, state);
            //Thread.sleep(((int) Math.random() * 1000) + 10000); Person waits a random time
            callElevator(directionButton);
        }

    }

    public int getDirection() {
        int direction = 0;
        //direction:
        /*
            1 - up
            2 - down
         */
        if (destinationFloor == currentFloor) {
            direction = 1; //change later
        } else if (destinationFloor > currentFloor) {
            direction = 1;
        } else if (destinationFloor < currentFloor) {
            direction = 2;
        }

        return direction;
    }

    
        public void characterEmoji(){
            //Thread.sleep(((int) Math.random() * 1000) + 10000);
            System.out.println("[" + personName + "]: I wonder where the reception is...");
        System.out.println("[" + personName + "]: I think its on floor " + destinationFloor + "!");
        System.out.println("[" + personName + "]: I'm calling the elevator");
        System.out.println("[SYSTEM]: " + personName + " has pressed the elevator button");
    }

    public void callElevator(int directionButton) {
        try {
            m.pressElevatorButton(directionButton, 1);
        } catch (Exception e) {
            //System.out.println("Error: " + e);
        }
    }
    
    

}
