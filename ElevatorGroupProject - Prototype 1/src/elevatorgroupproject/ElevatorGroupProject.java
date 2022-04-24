/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package elevatorgroupproject;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Asher
 */
public class ElevatorGroupProject {

    //Init Var
    int e1Destination, e2Destination, e1Weight, e2Weight, e1CurrentFloor, e2CurrentFloor, e1State, e2State;
    //linked list is used to store objects in an array to keep track the number of users inside the elevator.
    LinkedList passengerElevator1 = new LinkedList();
    LinkedList passengerElevator2 = new LinkedList();

    //Create the thread of people who are going to use the elevator. These values will be random.
    Person p1 = new Person(name, weight, destinationfloor, status, currentfloor, personid, personName);
    Person p2 = new Person();
    Person p3 = new Person();
    Person p4 = new Person();

    //variables - (int) e1State or e2State
    /*
        state = 0 - Elevator is idle
        state = 1 - Elevator is moving up
        state = 2 - Elevator is moving down.
    
     */
    //syncrhonized thread 1
    public synchronized void elevator1() throws InterruptedException { 
        while(){
            
        }
    }

    public synchronized void elevator2() throws InterruptedException {

    }

    //Method
    /*
        Adds a person to the elevator based on the elevator's current floor, the person's current floor and if the person is waiting for the elevator.
     */
    public void addPersonToElevator(boolean elevatorType) {
        //(boolean) elevatortype helps us determine which elevator is which
        //False = Elevator 1
        //True =  Elevator 2

        //Init Var
        String message = " has been added to the elevator.", personName;
        LinkedList joinElevatorQueue = new LinkedList();
        int destFloor, p1Status;

        //Check which elevator we are adding people inside.
        if (elevatorType == false) { // - elevator 1

            //Get People on Current Floor
            //There will be a scenario where there will be multiple people per floor. First we need to check how many people are on the same floor as the elevator.
            if (e1CurrentFloor == p1.getCurrentFloor()) {
                //If the person is in the same floor, then we add them to the queue.
                joinElevatorQueue.add(p1);
            }
            if (e1CurrentFloor == p2.getCurrentFloor()) {
                joinElevatorQueue.add(p2);
            }
            if (e1CurrentFloor == p3.getCurrentFloor()) {
                joinElevatorQueue.add(p3);
            }
            if (e1CurrentFloor == p4.getCurrentFloor()) {
                joinElevatorQueue.add(p4);
            }

            //Determine Elevator Direction
            //Now, from the queue, which of the people are actually going to the same direction (For example: P1 wants to go up, P2 wants to go down, P3 wants to go up.
            //The first person in the queue will determine the direction to which the elevator will go to.
            if (joinElevatorQueue.getFirst() == p1) {
                //If the person's destination is higher than the current elevator's floor, then we set the elevator state to GOING UP
                if (p1.getDestination >= e1CurrentFloor) {
                    e1State = 1;
                } else {
                    e1State = 2;
                }
            } // TODO: REPEAT

            //Remove Opposite Direction People
            //Now that we determined the elevator's direction, we don't want to allow people to go into the elevator and going to the opposite direction.
            if (joinElevatorQueue.contains(p1)) {
                if (p1.getStatus != e1State) {
                    //For example: getStatus = 1 means that person is waiting and destination is floor(s) above. e1State means that the elevator is going up.
                    //If the values are different, then we can assume thatt they are not going into the same direction.
                    joinElevatorQueue.remove(p1);
                }
            } // TODO: REPEAT

            //Add People to The Elevator
            if (joinElevatorQueue.contains(p1)) {
                passengerElevator1.add(p1);
                displayNotifs(p1.getName() + message, false);

            } // TODO: REPEAT

        } else {

        }

    }

    public int checkPassenger(LinkedList joinElevatorQueue, int passengerType) {
        //This method will help perform comparison checks without calling it all the time.

        int passengerResult = -1; //If passenger A/1 exists, then we make passengerResult=1 and return that value.

        if (joinElevatorQueue.contains(passengerType)) {
            passengerResult = 1;
        }

        return passengerResult;

    }

    public void goToDestination(boolean elevatorType) {
        //(boolean) elevatortype helps us determine which elevator is which
        //False = Elevator 1
        //True =  Elevator 2

        //Init Var
        int p1Destination, p2Destination, p3Destination, p4Destination, noPassOccupied;
        int[] pFloorDestination = {-1, -1, -1, -1};
        //pFloorDestination Guide
        /*
            index 0 - passenger 1
            index 1 - passenger 2
            index 2 - passenger 3
            index 3 - passenger 4
         */
        if (e1State == 0) {
            //If the elevator is not doing anything, then it must be waiting for people.
            //Let's start by checking if any of the available floors have people waiting.
            
            //Init Var
            
            if (p1.getDestination() != -1) {
                //-1 means the person has not reached their destination and have not travelled through the elevator before.
                pFloorDestination[0] = p1.getDestinationFloor();
            } else if (p2.getDestination() != -1){
                pFloorDestination[1] = p1.getDestinationFloor();
            }
        } else if (elevatorType == false) {
            //Check if elevator is moving up
            if (e1State == 1) {
                //The elevator has not moved yet, so we need to move it by going to its destination floor by incrementing the e1CurrentFloor.
                //Before we do that, we need to check the first destination floor by going through each person's destination floor and getting the smallest destination floor so that
                //the elevator goes to the floor sequentially.
                //Additionally, we want to iterate this process if there is multiple people in the elevator.
                
                //Init Var
                for(int i=0; i<4; i++){
                    //Reset pFloorDestination so it can be used for this module.
                    pFloorDestination[i] = -1;
                }
                
                //Determine how many people are in the elevator.
                noPassOccupied = passengerElevator1.size();

                while (passengerElevator1.isEmpty()) {
                    if (passengerElevator1.contains(p1)) {
                        pFloorDestination[0] = p1.getDestinationFloor();
                    }
                    if (passengerElevator1.contains(p2)) {
                        pFloorDestination[1] = p2.getDestinationFloor();
                    }
                    if (passengerElevator1.contains(p3)) {
                        pFloorDestination[2] = p3.getDestinationFloor();
                    }
                    if (passengerElevator1.contains(p4)) {
                        pFloorDestination[3] = p4.getDestinationFloor();
                    }

                    //Sort the destination variables to find the smallest.
                    Arrays.sort(pFloorDestination);

                    //Set the elevator's destination to the smallest value.
                    if (pFloorDestination[0] != -1) {
                        noPassOccupied = pFloorDestination[0];
                    }

                    //Each time the elevator moves up the floor, we need to check if there is a passenger with t he same floor destination.
                    //The reason why we have this check before the move elevator++, is because of the chance that someone might click on the same floor.
                    if (e1Destination == pFloorDestination[0]) {
                        //Determine which passenger has the same pFloorDestination.
                        if (pFloorDestination[0] == p1.getDestinationFloor()) {
                            //Now we remove the passenger out of the elevator.
                            removePassenger(1, false);
                            displayNotifs("Has arrived at Floor " + e1CurrentFloor, false);
                            //We also need to readjust the array
                            pFloorDestination[0] = -1;
                        }
                    } //TODO: REPEAT

                    //Move the elevator up a floor.
                    e1CurrentFloor++;
                    displayNotifs("Is moving up a floor. Currently in Floor " + e1CurrentFloor, false);

                }

            }
        } else {

        }
    }

    //Method
    /*
        Displays notifications

     */
    public void displayNotifs(String message, boolean elevatorType) {
        //(boolean) elevatortype helps us determine which elevator is which
        //False = Elevator 1
        //True =  Elevator 2
        String elevatorName;
        if (elevatorType == false) {
            elevatorName = "Elevator 1";
        } else {
            elevatorName = "Elevator 2";
        }

        System.out.println("[" + elevatorName + "]: " + message);

    }

    //Method
    /*
        Checks the people's weight inside the elevator.
     */
    public void checkPersonWeight(boolean elevatorType) {
        //Person p1 = new Person
        //First check which elevator is calling this method.
        //False = Elevator 1
        //True =  Elevator 2

        //Init Var
        float accumWeight = 0; //Accumulate Weight

        //For elevator 1
        if (elevatorType == false) {
            //First we check if the linked list is emtpy or not.
            if (!passengerElevator1.isEmpty()) {
                //If it is not empty, we can assume that a person is inside the elevator.
                //Next we check if P1(passenger 1) or P2, P3, P4 are in that node of tthe linekd list.
                if (passengerElevator1.contains(p1)) {
                    //If object does exist inside the linked list, we accumulate the passengers weight to accumWeight (float).
                    //But we also need to check if the passengers themselves are not over 1000kg.
                    if (p1.returnWeight() >= 1000) {
                        removePassenger(1, false);
                    } else {
                        accumWeight += p1.returnWeight();
                    }
                } else if (passengerElevator1.contains(p2)) {
                    if (p2.returnWeight() >= 1000) {
                        removePassenger(2, false);
                    } else {
                        accumWeight += p2.returnWeight();
                    }
                } else if (passengerElevator1.contains(p3)) {
                    if (p3.returnWeight() >= 1000) {
                        removePassenger(3, false);
                    } else {
                        accumWeight += p3.returnWeight();
                    }
                } else if (passengerElevator1.contains(p4)) {
                    if (p4.returnWeight() >= 1000) {
                        removePassenger(4, false);
                    } else {
                        accumWeight += p4.returnWeight();
                    }
                }

                //check if accumWeight is pass 1000kg
                if (accumWeight >= 1000) {
                    //If it is true, then remove the last person.
                    if (passengerElevator1.getLast() == p1) {
                        accumWeight -= p1.returnWeight();
                        removePassenger(1, false);
                    } else if (passengerElevator1.getLast() == p2) {
                        removePassenger(2, false);
                        accumWeight -= p2.returnWeight();
                    } else if (passengerElevator1.getLast() == p3) {
                        removePassenger(3, false);
                        accumWeight -= p3.returnWeight();

                    } else if (passengerElevator1.getLast() == p4) {
                        removePassenger(4, false);
                        accumWeight -= p4.returnWeight();

                    }
                }

            }
            //For elevator 2
        } else {

        }
    } //nice

    public void removePassenger(int passengerId, boolean elevatorType) {
        if (elevatorType == false) {
            if (passengerId == 1) {
                //Set the destination to something unreasonable
                p1.setDestination(99);
                p1.setStatus(4);
                if (passengerElevator1.contains(p1)) {
                    passengerElevator1.remove(p1);
                }
            } else if (passengerId == 2) {
                //Set the destination to something unreasonable
                p2.setDestination(99);
                p2.setStatus(4);
                if (passengerElevator1.contains(p1)) {
                    passengerElevator1.remove(p1);
                }
            } else if (passengerId == 3) {
                //Set the destination to something unreasonable
                p3.setDestination(99);
                p3.setStatus(4);
                if (passengerElevator1.contains(p1)) {
                    passengerElevator1.remove(p1);
                }
            } else if (passengerId == 4) {
                //Set the destination to something unreasonable
                p4.setDestination(99);
                p4.setStatus(4);
                if (passengerElevator1.contains(p1)) {
                    passengerElevator1.remove(p1);
                }
            }
        } else {

        }

    }

}
