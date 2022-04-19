/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InfiniteElevator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Asher
 */

public class BuildingFloor {

    //Used to print the building floor
    private LinkedList buildFloorPrint = new LinkedList<>();

    //ExRoute contains the passengers that have been picked up by the elevator
    private Map<String, Integer> e1Route = new HashMap<String, Integer>();
    private Map<String, Integer> e2Route = new HashMap<String, Integer>();

    private int passengerTotal = 10; //Number of passengers

    private Passenger[] pass = new Passenger[passengerTotal];

    private int capacity = 4; //Number of Building Floor

    //Elevator direction
    private int e1KnownDirection = -1;
    private int e2KnownDirection = -1;

    //Skipped Passengers
    private int skippedPassengers;

    //Graph Variables
    private LinkedList postElevator1Passenger = new LinkedList<>();
    private LinkedList postElevator2Passenger = new LinkedList<>();
    private int walkedAwayPassengers = 0;
    private LinkedList<GraphPassTimeTest> passDestTime = new LinkedList<>();
    private long startNanoTime = 0;
    private long endNanoTime = 0;
    
    //Generate the direction based on the passenger's current floor and destination floor
    private int generateDirection(int passengerCurrentFloor, int passengerDestinationFloor) {
        int direction = 2;
        //direction:
        /*
            1 - up
            2 - down
         */
        if (passengerDestinationFloor == passengerCurrentFloor) {
            direction = 0;
        } else if (passengerDestinationFloor > passengerCurrentFloor) {
            direction = 1;
        } else if (passengerDestinationFloor < passengerCurrentFloor) {
            direction = 2;
        }
        return direction;
    }

    //Generate passenger, destination, direction and current floor and distribute them in the building
    public void distributePeople() {

        //int numberOfPassengers = 0;
        //Alphabet to determine code of passengers
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        LinkedList<String> usedAlpha = new LinkedList<>();

        //For each passenger 
        for (int i = 0; i < passengerTotal; i++) {
            String alpha = alphabet[(int) (Math.random() * alphabet.length + 0)];
            boolean same = true;

            //Check if the alphabet is used already
            while (same) {
                if (usedAlpha.contains(alpha)) {
                    alpha = alphabet[(int) (Math.random() * alphabet.length + 0)];
                } else {
                    same = false;
                }
            }
            //Randomly generate a destination, direction and current numbers of the passengers
            same = true;
            int destination = (int) (Math.random() * 4 + 1);
            int current = (int) (Math.random() * 4 + 1);
            int direction = generateDirection(current, destination);
            usedAlpha.add(alpha);

            //debug
            System.out.println("" + alpha + "(current): " + current + " | destination " + destination + " | direction: " + direction);

            //System.out.println("Direction: " + direction);
            pass[i] = new Passenger(alpha, (int) (Math.random() * 1000 + 600), (int) destination, (int) current, (int) direction);

            //numberOfPassengers++;
        }

        //Print elevator floor, labels and passengers inside the elevator
        printElevator();

        //System.out.println(listOfPassengers);
    }

    //Prints the elevator's floor, labels and passengers inside the floor
    private void printElevator() {
        //Init var
        String concat = "";
        buildFloorPrint.clear();

        //Generate the floors including the passengers on that floor [  ABC  ]
        int countTotalPassengers = 0;
        for (int floor = 1; floor < capacity + 1; floor++) {
            countTotalPassengers = 0;
            concat = "";
            for (int x = 0; x < passengerTotal; x++) {
                if (pass[x] != null) {
                    if (floor == pass[x].getCurrent()) {
                        String passengerCode = pass[x].getID();
                        concat = "" + concat + passengerCode;
                        //System.out.println("Concat: " + concat);
                    } else {
                        countTotalPassengers++;
                    }
                } else {
                    countTotalPassengers++;
                }
            }
            if (countTotalPassengers == passengerTotal) {
                buildFloorPrint.add("-----");
            } else {
                buildFloorPrint.add(concat);
            }
        }

        //Print the floors and its labels
        System.out.println("");
        //Print the floor number
        for (int i = 0; i < buildFloorPrint.size(); i++) {
            System.out.printf("%s%10s%10s", "[", "FLOOR " + (i + 1), "]");
        }
        System.out.println("");
        //Print the floors
        for (int i = 0; i < buildFloorPrint.size(); i++) {
            System.out.printf("%s%10s%10s", "[", buildFloorPrint.get(i), "]");
        }

        System.out.println("");
    }

    //Drops off the person based on the key codes of the passengers inside the elevator
    private void dropOffPerson(int elevatorCurrentFloor, int elevatorType, Map<String, Integer> eRoute) {
        //Check if the person in the elevator has their destination as the current floor in the elevator
        String elevatorContainer = "";

        //Set name of the notification system
        if (elevatorType == 1) {
            elevatorContainer = "ELEVATOR 1";
        } else if (elevatorType == 2) {
            elevatorContainer = "ELEVATOR 2";
        }

        /*
        Note: There is a problem using for loops with hash maps for some reason, so I converted the hash map to a linkedlist
              so that it plays nice with the for loop
        TODO: Find possible fix.
         */
        //Check for passenger code 'A'/'B'.. etc, then check their destination
        LinkedList passengerKeys = new LinkedList(eRoute.keySet());
        for (int i = 0; i < passengerKeys.size(); i++) {
            String passengerKeyCode = passengerKeys.get(i).toString();
            int passDestFloor = eRoute.get(passengerKeyCode);

            //If the passenger's destination floor is on the same as the elevator, drop the passenger off.
            if (passDestFloor == elevatorCurrentFloor) {

                //Remove the passenger from the elevator
                eRoute.remove(passengerKeyCode);
                System.out.println("\n[" + elevatorContainer + "]: HAS DROPPED OFF " + passengerKeyCode);

                //If elevator 2 is calling this method, that means elevator 2 has dropped a passenger off that elevator 1 skipped.
                if (elevatorType == 2) {
                    endNanoTime = System.nanoTime();
                    long differenceTime = endNanoTime - startNanoTime;
                    skippedPassengers--;
                    //Graph Data for Elevator 1 Passengers
                    postElevator2Passenger.add(passDestFloor);
                    passDestTime.add( new GraphPassTimeTest(passengerKeyCode, passDestFloor, differenceTime));
                } else {
                    //Graph Data for Elevator 1 Passengers
                    endNanoTime = System.nanoTime();
                    long differenceTime = endNanoTime - startNanoTime;
                    postElevator1Passenger.add(passDestFloor);
                    passDestTime.add( new GraphPassTimeTest(passengerKeyCode, passDestFloor, differenceTime));

                }
            }
        }
    }

    //Picks up the passengers based on the direction the elevator is heading to.
    //Assign a direction for the elevator if the elevator doesn't know it.
    private void commenceJourney(int elevatorCurrentFloor, int elevatorType, Map eRoute, int knownDirection) {
        String elevatorContainer = "";

        //Determines printing label for elevator
        if (elevatorType == 1) {
            elevatorContainer = "ELEVATOR 1";
        } else if (elevatorType == 2) {
            elevatorContainer = "ELEVATOR 2";
        }

        for (int i = 0; i < pass.length; i++) {
            if (pass[i] != null) {
                //If the direction is not known to the elevator, the first person to be picked up will assume the rest of the elevator's direction journey
                //But if the direction is either 1 or 2 (known), then the elevator will only pick up people based on that direction

                //If the passenger at the floor same commits elevator-dash then we do this specific thing to them
                if (elevatorCurrentFloor == pass[i].getCurrent() && pass[i].getDirection() == 0 && pass[i] != null) {
                    System.out.println("");
                    System.out.println(pass[i].getID() + " walked away..");
                    walkedAwayPassengers++;
                    pass[i] = null;
                }
                switch (knownDirection) {
                    //IF elevator's known direction is unknown, determine by the first person we pick up
                    //Set the elevator 1 direction based on the first person's direction
                    case -1 -> {
                        if (pass[i] != null) {
                            if (elevatorCurrentFloor == pass[i].getCurrent() && pass[i] != null) {
                                System.out.println("\n[" + elevatorContainer + "]: HAS PICKED UP " + pass[i].getID());
                                eRoute.put(pass[i].getID(), pass[i].getDestination());
                                knownDirection = (int) pass[i].getDirection();
                                e1KnownDirection = knownDirection;
                                pass[i] = null;
                            }
                        }

                        break;
                    }
                    //If the passenger destination is on the current floor they are in CURRENT = DESTINATION, then they just walk away.
                    case 0 -> {
                        if (elevatorCurrentFloor == pass[i].getCurrent() && pass[i].getDirection() == 0 && pass[i] != null) {
                            System.out.println("");
                            System.out.println(pass[i].getID() + " walked away..");
                            pass[i] = null;
                        }
                        break;
                    }
                    //Elevator will pick someone up on the current floor and store it in elevator's own route list.
                    default -> {
                        if (pass[i] != null) {
                            if (elevatorCurrentFloor == pass[i].getCurrent()) {
                                if (knownDirection == pass[i].getDirection()) {
                                    System.out.println("");
                                    System.out.println("[" + elevatorContainer + "]: HAS PICKED UP " + pass[i].getID());
                                    eRoute.put(pass[i].getID(), pass[i].getDestination());
                                    pass[i] = null;
                                } else if (knownDirection != pass[i].getDirection() && elevatorType == 1) {
                                    skippedPassengers++;
                                    System.out.println("\n[" + elevatorContainer + "]: HAS SKIPPED " + pass[i].getID());
                                }
                            }
                        }
                        break;
                    }
                }
            }

        }

    }

    //Print the passengers inside the elevator
    private void showElevatorPassenger(int elevatorType, String customFormat, Map<String, Integer> eRoute) {
        String elevatorContainer = "";

        //Determine elevator string to print based on elevator type
        if (elevatorType == 1) {
            elevatorContainer = "E1";
        } else if (elevatorType == 2) {
            elevatorContainer = "E2";
        }

        //Print elevator as well as the passengers that the elevator has picked up
        String passengerCodeList = "";
        if (!eRoute.isEmpty()) {
            elevatorContainer = elevatorContainer + "(";
            for (String passengerKey : eRoute.keySet()) {
                passengerCodeList = passengerCodeList + passengerKey;
            }
            elevatorContainer = elevatorContainer + passengerCodeList + ")";
        }

        System.out.printf(customFormat, "<", elevatorContainer, ">");
    }

    //Print all of the elevator operations
    private void printElevator1Operation(int floor, String customMessage) {
        printElevator(); //print elevator floor
        showElevatorPassenger(1, customMessage, e1Route);       //print >    E1     <
        dropOffPerson(floor, 1, e1Route);                       //drop person off
        commenceJourney(floor, 1, e1Route, e1KnownDirection);  //makes the elevator check if the floor has passengers and picks up the person
    }

    //Print all of the elevator operations
    private void printElevator2Operation(int floor, String customMessage) {
        printElevator(); //print elevator floor
        showElevatorPassenger(2, customMessage, e2Route);
        dropOffPerson(floor, 2, e2Route); //drop person off
        commenceJourney(floor, 2, e2Route, e2KnownDirection); //makes the elevator check if the floor has passengers and picks up the person
    }

    //Determine whether the elevator should go UP a floor or DOWN a floor
    private boolean checkPassengersAbove(boolean passengersAbove, int direction, int floor, Map<String, Integer> eRoute) {
        //Elevator will go up if the elevator direction is going up
        if (direction == 1) {
            passengersAbove = true;
        } else {
            passengersAbove = false;
        }
        //Eelvator will go up if the elevator is empty
        if (eRoute.isEmpty()) {
            passengersAbove = true;
        }

        //If there are no more passengers
        if (pass.length == 0) {
            passengersAbove = false;
        }
        //If the elevator direction is not know, just go up so we can check each floor for passengers.
        if (direction == -1) {
            passengersAbove = true;
        }

        return passengersAbove;
    }

    public void generateSummary() {
        //      public GraphStuff(int floor, int option, String applicationTitle , String chartTitle, String sideCat, String botCat, LinkedList e1Data, LinkedList e2Data) {
        
        
        //Graph 1 - Passenger Distribution
        GraphStuff postElevatorGraph = new GraphStuff(capacity, 1, "Elevator Summary", "Post Elevator Data of Passenger Destination", "Passengers", "Floors", postElevator1Passenger, postElevator2Passenger, walkedAwayPassengers, passDestTime);
        postElevatorGraph.pack();
        RefineryUtilities.centerFrameOnScreen(postElevatorGraph);
        postElevatorGraph.setVisible(true);

        //Graph 2 - Summary (text)
        GraphStuff elevatorSummary = new GraphStuff(capacity, 2, "Elevator Summary", "How many passengers arrived?", "Passengers", "Floors", postElevator1Passenger, postElevator2Passenger, walkedAwayPassengers, passDestTime);
        elevatorSummary.pack();
        RefineryUtilities.centerFrameOnScreen(elevatorSummary);
        elevatorSummary.setVisible(true);

        //Graph 3 - Summary (graph)
        GraphStuff elevatorSummaryGraph = new GraphStuff(capacity, 3, "Elevator Summary", "Post Elevator Summary Bar Chart", "Passengers", "Category", postElevator1Passenger, postElevator2Passenger, walkedAwayPassengers, passDestTime);
        elevatorSummaryGraph.pack();
        RefineryUtilities.centerFrameOnScreen(elevatorSummaryGraph);
        elevatorSummaryGraph.setVisible(true);
        
         //Graph 3 - Time Graph (graph)
        GraphStuff elevatorTimeGraph = new GraphStuff(capacity, 4, "Elevator Summary", "Elevator Time Graph", "Time Taken", "Passengers", postElevator1Passenger, postElevator2Passenger, walkedAwayPassengers, passDestTime);
        elevatorTimeGraph.pack();
        RefineryUtilities.centerFrameOnScreen(elevatorTimeGraph);
        elevatorTimeGraph.setVisible(true);
    }

    // Function called by producer thread
    public void elevator1() throws InterruptedException {
        int floor = 1;
        while (floor <= 4 && floor != 0) {
            synchronized (this) {

                while (skippedPassengers > 0 || pass.length == 0) {
                    wait();
                }
                
                startNanoTime = System.nanoTime();


                switch (floor) {
                    case 1:
                        printElevator1Operation(floor, "%s%10s%10s");

                        break;
                    case 2:
                        printElevator1Operation(floor, "%22s%10s%10s");

                        break;
                    case 3:
                        printElevator1Operation(floor, "%43s%10s%10s");
                        break;
                    case 4:
                        printElevator1Operation(floor, "%64s%10s%10s");

                        break;
                    default:
                        break;
                }

                boolean passengersAbove = false;
                passengersAbove = checkPassengersAbove(passengersAbove, e1KnownDirection, floor, e1Route);

//                System.out.println("e1KnownDirection" + e1KnownDirection);
                if ((passengersAbove || e1KnownDirection == -1)) {
                    floor++;
                } else {
                    floor--;
                }

                //System.out.println("e1Route" + e1Route);
                if (floor == 5 && e1Route.isEmpty()) {
                    System.out.println("\n[ELEVATOR 1] Has finished its operations!");
                } else if (floor == 5 && !e1Route.isEmpty()) {
                    floor--;
                }

                notify();
                Thread.sleep(1);

            }
        }
    }

    // Function called by consumer thread
    public void elevator2() throws InterruptedException {
        int floor = 1;
        while (floor <= 4 && floor != 0) {
            synchronized (this) {
                // consumer thread waits while list
                // is empty
                while (skippedPassengers == 0 && pass.length == 0) {
                    wait();
                }
                
                startNanoTime = System.nanoTime();

                //Elevator 2 will pick up passengers the opposite direction
                if (e1KnownDirection == 1) {
                    e2KnownDirection = 2;
                } else if (e1KnownDirection == 2) {
                    e2KnownDirection = 1;
                }

                //For each floor, print the floor, drop off any passengers (if any), pick up any passengers (if any), print the elevator with passengers (if any)
                switch (floor) {
                    case 1:
                        printElevator2Operation(floor, "%s%10s%10s");

                        break;
                    case 2:
                        printElevator2Operation(floor, "%22s%10s%10s");

                        break;
                    case 3:
                        printElevator2Operation(floor, "%43s%10s%10s");
                        break;
                    case 4:
                        printElevator2Operation(floor, "%64s%10s%10s");

                        break;
                    default:
                        break;
                }
//                System.out.println("2 skippedPassengers = " + skippedPassengers);
//                System.out.println("2 e2KnownDirection" + e2KnownDirection);
                boolean passengersAbove = false;

                //Determins if the elevator should go up or down
                passengersAbove = checkPassengersAbove(passengersAbove, e2KnownDirection, floor, e2Route);

                if ((passengersAbove || e2KnownDirection == -1)) {
                    floor++;
                } else {
                    floor--;
                }
                if (floor == 5) {
                    System.out.println("\n[ELEVATOR 2] Has finished its operations!");
                }

                notify();
                Thread.sleep(1);

            }
        }
    }
}
