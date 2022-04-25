/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package elevatortesting2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Asher
 */
//Temporarily hold the person's data inside the elevator so that we can use it throughout the code.
class tempPerson {

    private String name;

    private int destination;

    private int current;

    private int state;

    private int direction;

    private long personID;

    tempPerson(long personID, String name, int destination, int current, int state, int direction) {
        this.personID = personID;
        this.name = name;
        this.destination = destination;
        this.current = current;
        this.state = state;
        this.direction = direction;
    }

    //Getter
    public String getName() {
        return name;
    }

    public int getDestination() {
        return destination;
    }

    public int getCurrent() {
        return current;
    }

    public int getState() {
        return state;
    }

    public long getID() {
        return personID;
    }

    public int getDirection() {
        return direction;
    }

    //Setter
    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setState(int state) {
        this.state = state;
    }

}

public class ElevatorMutex {

    private int e1Destination, e2Destination, e1Weight, e2Weight, e1CurrentFloor = 3, e2CurrentFloor = 3, e1State = 0, e2State = 0;
    private LinkedList<tempPerson> personQStack = new LinkedList<>();
    private LinkedList<tempPerson> e1Passengers = new LinkedList<>();
    private LinkedList<tempPerson> e2Passengers = new LinkedList<>();
    private LinkedList e1DestinationQ = new LinkedList();
    private LinkedList e1CurrentQ = new LinkedList();

    private final int buildingFloors = 4;
    private final int elevatorPeopleCapacity = 20;

    //debug
    /*
    
    
    
     */
    
    boolean flag = false;
    public synchronized int elevator1() throws InterruptedException {
        // System.out.println("Person Stack " + personQStack);
        //Identify possible passengers who are not assigned to e2
        while (flag == true) {
            System.out.println("Is busy");
        }
        boolean e2 = checkE2Traffic();
        //False if there is no traffic
        if (e2 == false || e2 == true && e1State < 1) {
            //If multiple called the elevator at once, identify which one is the most important floor to go to.
            int e1Busy = identifyPriorityFloor(1);
            if (e1Busy == 1) {
                collectPassengersFromCurrentFloor(1);
                Thread.sleep(1000);
                flag = true;
                notify();
            }
        }

        return 0;
    }

    public synchronized void elevator2() throws InterruptedException {
        while (flag == false) {
            System.out.println("E2 is busy");
        }
        boolean e1 = checkE1Traffic();
        //False if there is no traffic
        if (e1 == true) {
            //If multiple called the elevator at once, identify which one is the most important floor to go to.
            int e1Busy = identifyPriorityFloor(2);
            if (e1Busy == 1) {
                System.out.println("Calling this 11111111111");
                collectPassengersFromCurrentFloor(2);
                Thread.sleep(1000);
                flag = false;
                notify();
            }
        }
    }

    public void calculateWeight() {

    }

    public void enterElevatorArea(int personDestinationFloor, int personCurrentFloor, String personName, int personCurrentState) {
        //Add person to the elevator waiting queue stack
        personQStack.add(new tempPerson(Thread.currentThread().getId(), personName, personDestinationFloor, personCurrentFloor, personCurrentState, 0));
    }

    public void pressElevatorButton(int personDirection, int personState) {
        //Set direction and new state of the person
        for (tempPerson tempPersonCall : personQStack) {
            if (tempPersonCall.getID() == Thread.currentThread().getId()) {
                tempPersonCall.setDirection(personDirection);
                tempPersonCall.setState(personState);
            }
        }
        notifyElevators();
        System.out.println("personQStack: " + personQStack);

    }

    public void notifyElevators() {
        try {
            if (flag == false) {
               elevator1();
            } else {
                 elevator2();
            }
        } catch (InterruptedException e) {

        }

    }

    public Boolean checkE1Traffic() {
        return e1State != 0; //returns false if 0, meaning it is idle.
    }

    public Boolean checkE2Traffic() {
        return e2State != 0; //returns false if 0, meaning it is idle.
    }

    public int identifyPriorityFloor(int elevatorType) {
        //Find out from a group of people who has floor priority.
        //For example: PERSON A and B are in the same elevator. A wants is in floor 3, B is in 4. How does the elevator know which one to go?
        //First we need to identify if the elevator is going up or down. The first person who calls the elevator will do that.
        System.out.println("identifyPriorityFloor");
        int[] getPeopleDestination = {};
        int sortingCounter = 0, firstPersonDirection = 0;

        if (e1State == 0 || e2State == 0) {
            //The first person in the stack is always the first person to call the elevator
            if (personQStack.get(0) != null && personQStack.get(0).getState() == 1) {
                firstPersonDirection = personQStack.get(0).getDirection();
            }
            e1State = 1;
            getPersonPickUpFloor(firstPersonDirection, elevatorType);
            System.out.println("identifyPriorityFloor: after getPersonPickup");
            return 1;
        } else {
            return 2; //e1 is doing something
        }

    }



    public void collectPassengersFromCurrentFloor(int elevatorType) {
        boolean flag = true; //force stop a while loop
        int counter = 0, personCurrentFloor = -1;
        e1CallRequest((int) e1CurrentQ.get(0), elevatorType);
    }

    public void getPersonPickUpFloor(int direction, int elevatorType) {
        //direction:
        /*
        
            1 = up
            2 = down
        
         */
        //elevatorType:
        /*
        
            1 = e1
            2 = e2
        
         */

        int sortingCounter = 0;
        sortingCounter = 0;
        System.out.println("getPersonPickUpFloor");

        for (tempPerson tempPersonCall : personQStack) {
            //Get the ppl's current floor if they are going up and their floor is higher than the elevator's current floor.
            //Once they collect, elevator will go straight to the destination.
            System.out.println("Direction: " + tempPersonCall.getDirection());
            System.out.println("tempPersonCall.getCurrent() : " + tempPersonCall.getCurrent());
            if(elevatorType == 1){
                if (tempPersonCall.getDirection() == 1 && tempPersonCall.getCurrent() <= e1CurrentFloor) {
                    e1DestinationQ.add(tempPersonCall.getDestination());
                    e1CurrentQ.add(tempPersonCall.getCurrent());
                    personQStack.remove();
                } else if (tempPersonCall.getDirection() == 2 && tempPersonCall.getCurrent() >= e1CurrentFloor) {
                    e1DestinationQ.add(tempPersonCall.getDestination());
                    e1CurrentQ.add(tempPersonCall.getCurrent());
                    personQStack.remove();
                }
            } else {
                if (tempPersonCall.getDirection() == 1 && tempPersonCall.getCurrent() <= e2CurrentFloor) {
                    e1DestinationQ.add(tempPersonCall.getDestination());
                    e1CurrentQ.add(tempPersonCall.getCurrent());
                    personQStack.remove();
                } else if (tempPersonCall.getDirection() == 2 && tempPersonCall.getCurrent() >= e2CurrentFloor) {
                    e1DestinationQ.add(tempPersonCall.getDestination());
                    e1CurrentQ.add(tempPersonCall.getCurrent());
                    personQStack.remove();
                }
                System.out.println("getPersonPickUpFloor: elevator 2");
            }


            sortingCounter++;
        }

        Collections.sort(e1DestinationQ);
        Collections.sort(e1CurrentQ);

        System.out.println("getPersonPickUpFloor: finished");
        System.out.println("e1CurrentQ: " + e1CurrentQ);
        System.out.println("e1DestinationQ: " + e1DestinationQ);

    }

//    public void dropOffPerson() {
//        for (tempPerson tempPersonCall : personQStack) {
//            if (tempPersonCall.getDestination() == e1CurrentFloor) {
//                System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: is dropping off " + tempPersonCall.getName());
//
//            }
//        }
//
//    }
    public void e1CallRequest(int personCurrentFloor, int elevatorType) {
        System.out.println("elevatorType: " + elevatorType);
        //If elevator  floor is greater than the person's current floor (For example: Elevator at FLOOR 3, Person calls elevator at FLOOR 1)
        //elevator will descend until Elevator current Floor == person Current Floor
        //Vice versa when elevator floor is lower than person's current floor position.
        if (elevatorType == 1) {
            if (e1CurrentFloor > personCurrentFloor) {
                System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: Elevator Ready to Descend");
                //Elevator Descends until it meets person's current floor.
                while (e1CurrentFloor != personCurrentFloor) {
                    System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: Elevator is moving down towards floor " + personCurrentFloor + ".");
                    e1CurrentFloor--;
                    System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: Elevator new current floor " + e1CurrentFloor);
                }
                System.out.println("Elevator 1 has arrived. Door has opened");
            } else if (e1CurrentFloor < personCurrentFloor) {
                System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: Elevator Ready to Ascend");

                //Elevator Descends until it meets person's current floor.
                while (e1CurrentFloor != personCurrentFloor) {
                    System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: Elevator is moving up towards floor " + personCurrentFloor + ".");
                    e1CurrentFloor++;
                    System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: Elevator new current floor " + e1CurrentFloor);
                }
                System.out.println("Elevator 1 has arrived");
            } else {
                //If person calls elevator on the same floor as the elevator
                System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: Elevator cannot move since requested floor is on the same floor as the elevator.");
            }
        } else {
            if (e2CurrentFloor > personCurrentFloor) {
                System.out.println("[ELEVATOR 2][FLOOR " + e2CurrentFloor + "]: Elevator Ready to Descend");
                //Elevator Descends until it meets person's current floor.
                while (e2CurrentFloor != personCurrentFloor) {
                    System.out.println("[ELEVATOR 1][FLOOR " + e2CurrentFloor + "]: Elevator is moving down towards floor " + personCurrentFloor + ".");
                    e2CurrentFloor--;
                    System.out.println("[ELEVATOR 1][FLOOR " + e2CurrentFloor + "]: Elevator new current floor " + e2CurrentFloor);
                }
                System.out.println("Elevator 1 has arrived. Door has opened");
            } else if (e2CurrentFloor < personCurrentFloor) {
                System.out.println("[ELEVATOR 1][FLOOR " + e2CurrentFloor + "]: Elevator Ready to Ascend");

                //Elevator Descends until it meets person's current floor.
                while (e2CurrentFloor != personCurrentFloor) {
                    System.out.println("[ELEVATOR 2][FLOOR " + e2CurrentFloor + "]: Elevator is moving up towards floor " + personCurrentFloor + ".");
                    e2CurrentFloor++;
                    System.out.println("[ELEVATOR 2][FLOOR " + e2CurrentFloor + "]: Elevator new current floor " + e2CurrentFloor);
                }
                System.out.println("Elevator 2 has arrived");
            } else {
                //If person calls elevator on the same floor as the elevator
                System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: Elevator cannot move since requested floor is on the same floor as the elevator.");
            }
        }

    }

//    public void goToDestination(int getPersonDestination, String personName) {
//        //We want the elevator to go to the person's destination floor. So check if elevator's current floor position 
//        // is lower or higher than person's destination floor. (Example: Elevator floor: 2, Person Destination Floor: 4) Elevator needs to go to floor 4 so we increment its current position 
//        //If so, ascend, if not, descend.
//        if (e1CurrentFloor == getPersonDestination) {
//            //If person's destination floor is at the same floor he is currently in (FOR SOME REAOSN), this happens
//            System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: For some reason " + personName + " destination floor on the same floor he is at. Shame.");
//        } else if (e1CurrentFloor < getPersonDestination) {
//            //Elevator ascends until it reaches person's destination floor.
//            System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: Elevator Ready to Ascend");
//            while (e1CurrentFloor != getPersonDestination) {
//
//                System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: Elevator is moving up towards " + personName + "'s floor " + getPersonDestination + ".");
//                e1CurrentFloor++;
//                System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: Elevator new current floor " + e1CurrentFloor);
//            }
//        } else if (e1CurrentFloor > getPersonDestination) {
//            System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: Elevator Ready to Descend");
//
//            //Elevator descends until it reaches person's destination floor.
//            while (e1CurrentFloor != getPersonDestination) {
//                System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: Elevator is moving down towards " + personName + "'s destination at floor " + getPersonDestination + ".");
//                e1CurrentFloor--;
//                System.out.println("[ELEVATOR 1][FLOOR " + e1CurrentFloor + "]: Elevator new current floor " + e1CurrentFloor);
//            }
//
//        }
//        //System.out.println("Debug: goToDestination");
//    }
}
