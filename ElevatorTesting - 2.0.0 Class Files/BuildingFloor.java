/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InfiniteElevator;

import java.text.DecimalFormat;
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

    //Program Settings
    private int passengerTotal = 10; //Number of passengers
    private Passenger[] pass = new Passenger[passengerTotal];
    private LinkedList<Passenger> passengers = new LinkedList<>();
    private int capacity = 4; //Number of Building Floor

    //Elevator direction
    private int e1KnownDirection = -1;
    private int e2KnownDirection = -1;

    //Elevator weight
    //Elevator Max Weight: 2722 according to elevation.wiki. Going to reduce this to 1000 so more chances of passengers not being picked up.
    //Elevator weight calculated AFTER the elevator weight itself which is 435kg but no need to put this in. Just reset to 0.
    private float e1Weight = 0;
    private float e2Weight = 0;
    private final int EMAXWEIGHT = 1000;
    private Map<String, Float> passWeight1Collection = new HashMap<String, Float>();
    private Map<String, Float> passWeight2Collection = new HashMap<String, Float>();

    //Skipped Passengers
    private int skippedPassengers = 0;
    private int skippedWeightPassengers = 0;

    //Graph Variables
    private LinkedList postElevator1Passenger = new LinkedList<>();
    private LinkedList postElevator2Passenger = new LinkedList<>();
    private int walkedAwayPassengers = 0;
    private LinkedList<GraphPassTimeTest> passDestTime = new LinkedList<>();
    private long startNanoTime = 0;
    private long endNanoTime = 0;

    public void ruleBuilding() {
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println(" > WELCOME TO THE JAVA BUILDING CREATED BY: AFIF, HAIKAL AND SALAH                                  |");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println(" > IN THIS BUILDING, GET READY TO EXPERIENCE THE FASTEST ELEVATOR EXPERIENCE OF YOUR LIFE!~         ");
        System.out.println(" > BUT BEFORE WE BEGIN, LET US START WITH SOME GROUND RULES!                             ");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println(" ⚠ 1 ELEVATOR CAN ONLY HOLD UP TO " + EMAXWEIGHT + "KG WEIGHT ");
        System.out.println(" ⚠ THERE ARE CURRENTLY " + passengerTotal + " PASSENGERS IN THIS BUILDING!");
        System.out.println(" ⚠ THERE ARE " + capacity + " FLOORS IN THIS BUILDING ");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println(" > THANK YOU AND ENJOY YOUR STAY!                                                                  ");
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

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
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println(" > BUILDING PASSENGER REPORT - LIST OF ALL PASSENGERS                                                                ");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("Passenger Name | Current Floor | Destination Floor | Direction | Weight");
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

            float passWeight = (float) (Math.random() * 100.00 + 10.00);
            float passBaggage = (float) (Math.random() * 600.00 + 0.01);
            passWeight = passWeight + passBaggage;
            DecimalFormat passFormat = new DecimalFormat("#.##");
            String showWeight = passFormat.format(passWeight);
            //debug

            System.out.println("-----------------------------------------------------------------------");
            System.out.println(alpha + "              | " + current + "             | " + destination + "                 | " + direction + "         | " + showWeight);
            pass[i] = new Passenger(alpha, (float) passWeight, (int) destination, (int) current, (int) direction);

        }

        System.out.println("\n\n\n----------------------------------------------------------------------------------------------------");
        System.out.println(" > BEGIN ELEVATOR OPERATIONS                                                          ");
        System.out.println("----------------------------------------------------------------------------------------------------\n");
        
        //Print elevator floor, labels and passengers inside the elevator
        printElevator();

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
    private void dropOffPerson(int elevatorCurrentFloor, int elevatorType, Map<String, Integer> eRoute, Map<String, Float> passWeightCollection, float eWeight) {
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
                //get current total weight based on number of passengers and set to new weight
                passWeightCollection.remove(passengerKeyCode);
                float newTotalWeight = getNewTotalWeight(passWeightCollection);
                eWeight = newTotalWeight;
//                System.out.println("[Map of " + elevatorContainer + " ]: " + passWeightCollection);
//                System.out.println("[Weight of " + elevatorContainer + " ]: " + eWeight);

                System.out.println("\n[" + elevatorContainer + "]: HAS DROPPED OFF " + passengerKeyCode);

                //If elevator 2 is calling this method, that means elevator 2 has dropped a passenger off that elevator 1 skipped.
                if (elevatorType == 2) {
                    endNanoTime = System.nanoTime();
                    long differenceTime = endNanoTime - startNanoTime;
                    skippedPassengers--;
                    //Graph Data for Elevator 2 Passengers
                    postElevator2Passenger.add(passDestFloor);
                    passDestTime.add(new GraphPassTimeTest(passengerKeyCode, passDestFloor, differenceTime));

                } else {
                    if(skippedWeightPassengers > 0){
                        skippedWeightPassengers--;
                    }
                    
                    //Graph Data for Elevator 1 Passengers
                    endNanoTime = System.nanoTime();
                    long differenceTime = endNanoTime - startNanoTime;
                    postElevator1Passenger.add(passDestFloor);
                    passDestTime.add(new GraphPassTimeTest(passengerKeyCode, passDestFloor, differenceTime));
                }
            }
        }

    }

    //Determines if elevator CAN or CANNOT pickup the passenger. Therefore: return a value of 0 if cannot. Likewise 1 if can.
    private int checkElevatorWeight(int elevatorType, float passWeight, int i) {
        String elevatorContainer = "";
        int returnValue = 0;

        //Determines printing label for elevator
        if (elevatorType == 1) {
            elevatorContainer = "ELEVATOR 1";
            if ((passWeight + e1Weight) > EMAXWEIGHT) {
                System.out.println("\n[" + elevatorContainer + "]: CANNOT PICK UP '" + pass[i].getID() + "' DUE TO EXCEEDING WEIGHT"
                        + "\n[" + elevatorContainer + " CURRENT WEIGHT]: " + e1Weight
                        + "\n[" + pass[i].getID() + " WEIGHT]: " + pass[i].getWeight() + ""
                        + "\n[TOTAL WEIGHT]:" + (e1Weight + pass[i].getWeight()) + "kg > " + EMAXWEIGHT + "kg");
                //System.out.println("\n[" + elevatorContainer + "]: CANNOT PICK UP '" + pass[i].getID() + "' DUE TO EXCEEDING WEIGHT OF (ELEVATOR CURRENT WEIGHT)" + e1Weight + " (PASSENGER WEIGHT)" + passWeight + " > (MAX ELEVATOR WEIGHT)" + EMAXWEIGHT);

                returnValue = 1;
            } else {
                returnValue = 0;
            }
        } else if (elevatorType == 2) {
            elevatorContainer = "ELEVATOR 2";
            if ((passWeight + e2Weight) > EMAXWEIGHT) {
                System.out.println("\n[" + elevatorContainer + "]: CANNOT PICK UP '" + pass[i].getID() + "' DUE TO EXCEEDING WEIGHT"
                        + "\n[" + elevatorContainer + " CURRENT WEIGHT]: " + e2Weight
                        + "\n[" + pass[i].getID() + " WEIGHT]: " + pass[i].getWeight() + ""
                        + "\n[TOTAL WEIGHT]:" + (e2Weight + pass[i].getWeight()) + "kg > " + EMAXWEIGHT + "kg");
                //System.out.println("\n[" + elevatorContainer + "]: CANNOT PICK UP '" + pass[i].getID() + "' DUE TO EXCEEDING WEIGHT OF (ELEVATOR CURRENT WEIGHT)" + e2Weight + " (PASSENGER WEIGHT)" + passWeight + " > (MAX ELEVATOR WEIGHT)" + EMAXWEIGHT);
                returnValue = 1;
            } else {
                returnValue = 0;
            }

        }
        return returnValue;
    }

    //Get new total weight and set it to either e1weight or e2weight
    private float getNewTotalWeight(Map passWeightCollection) {
        float newTotalWeight = 0;
        LinkedList passengerKeys = new LinkedList(passWeightCollection.keySet());
        for (int x = 0; x < passWeightCollection.size(); x++) {
            String passengerKeyCode = passengerKeys.get(x).toString();
            newTotalWeight += (float) passWeightCollection.get(passengerKeyCode);
        }

        //System.out.println("New Total Weight of: " + newTotalWeight);
        return newTotalWeight;

    }

    //Picks up the passengers based on the direction the elevator is heading to.
    //Assign a direction for the elevator if the elevator doesn't know it.
    private void commenceJourney(int elevatorCurrentFloor, int elevatorType, Map eRoute, int knownDirection, Map passWeightCollection) {
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
                    switch ((int) (Math.random() * 5 + 1)) {
                        case 1 -> {
                            System.out.println("[" + pass[i].getID() + "]: \"Err, I changed my mind. I'm just going to stay on this floor.\"");
                            break;
                        }
                        case 2 -> {
                            System.out.println("[" + pass[i].getID() + "]: \"Haha, I'm going to press the elevator button and walk away.\"");
                            break;
                        }
                        case 3 -> {
                            System.out.println("[" + pass[i].getID() + "]: \"I think I'll take the stairs.\"");
                            break;
                        }
                        case 4 -> {
                            System.out.println("[" + pass[i].getID() + "]: \"Is this going up or down? You know what, I'm just going to take the stairs.\"");
                            break;
                        }
                        case 5 -> {
                            System.out.println("[" + pass[i].getID() + "]: \"Ah! I forgot to do something, sorry I pressed the elevator!.\"");
                            break;
                        }
                        default -> {
                            System.out.println("[" + pass[i].getID() + "]: \"Press the wrong button! Sorry!\"");
                            break;
                        }

                    }
                    System.out.println("\n[" + pass[i].getID() + "]: HAS WALKED AWAY ");
                    walkedAwayPassengers++;
                    pass[i] = null;
                }
                switch (knownDirection) {
                    //IF elevator's known direction is unknown, determine by the first person we pick up
                    //Set the elevator 1 direction based on the first person's direction
                    case -1 -> {
                        if (pass[i] != null) {
                            if (elevatorCurrentFloor == pass[i].getCurrent() && pass[i] != null) {
                                if (checkElevatorWeight(elevatorType, pass[i].getWeight(), i) == 0) {
                                    //Print pickup
                                    System.out.println("\n[" + elevatorContainer + "]: HAS PICKED UP " + pass[i].getID());

                                    //Add passenger to elevator container
                                    eRoute.put(pass[i].getID(), pass[i].getDestination());

                                    //Add passanger to elevator weight container
                                    passWeightCollection.put(pass[i].getID(), pass[i].getWeight());

                                    //get current total weight based on number of passengers and set to new weight
                                    float newTotalWeight = getNewTotalWeight(passWeightCollection);

                                    if (elevatorType == 1) {
                                        e1Weight = newTotalWeight;
                                    } else if (elevatorType == 2) {
                                        e2Weight = newTotalWeight;
                                    }

                                    //Get direction of passenger and set to the elevator
                                    knownDirection = (int) pass[i].getDirection();
                                    if (elevatorType == 1) {
                                        e1KnownDirection = knownDirection;
                                    } else if (elevatorType == 2) {
                                        e2KnownDirection = knownDirection;
                                    }

                                    //Destroy the passenger from the class object array
                                    pass[i] = null;
                                } else {
                                    //Skip passengers if they are overweight
                                    skippedWeightPassengers++;
                                    System.out.println("\n[" + elevatorContainer + "]: HAS SKIPPED " + pass[i].getID());
                                }

                            }

                        }
                        break;
                    }
                    //If the passenger destination is on the current floor they are in CURRENT = DESTINATION, then they just walk away.
                    case 0 -> {
                        if (elevatorCurrentFloor == pass[i].getCurrent() && pass[i].getDirection() == 0 && pass[i] != null) {
                            System.out.println("");
                            float endNanoTime = System.nanoTime();
                            float differenceTime = endNanoTime - startNanoTime;
                            differenceTime = differenceTime / 1_000_000_000;
                            
                            switch ((int) (Math.random() * 5 + 1)) {
                                case 1 -> {
                                    System.out.println("[" + pass[i].getID() + "]: \"I CANNOT BELIEVE THIS ELEVATOR TOOK " + differenceTime + " SECONDS!!!\"" );
                                    break;
                                }
                                case 2 -> {
                                    System.out.println("[" + pass[i].getID() + "]: \"Does this elevator go to the roof? Oh wait, it's the stairs! Silly me, sorry I pressed the elevator!\"");
                                    break;
                                }
                                case 3 -> {
                                    System.out.println("[" + pass[i].getID() + "]: \"I waiting so long for this elevator, I am just going to take the stairs!\"");
                                    break;
                                }
                                case 4 -> {
                                    System.out.println("[" + pass[i].getID() + "]: \"Is this going up or down? You know what, I'm just going to take the stairs.\"");
                                    break;
                                }
                                case 5 -> {
                                    System.out.println("[" + pass[i].getID() + "]: \"I forgot to get my suitcase in my office.\"");
                                    break;
                                }
                                default -> {
                                    System.out.println("[" + pass[i].getID() + "]: \"I forgot to pickup my parcel!\"");
                                    break;
                                }

                            }
                            System.out.println("\n[" + pass[i].getID() + "]: HAS WALKED AWAY ");
                            walkedAwayPassengers++;
                            pass[i] = null;
                        }
                        break;
                    }
                    //Elevator will pick someone up on the current floor and store it in elevator's own route list.
                    default -> {
                        if (pass[i] != null) {
                            if (elevatorCurrentFloor == pass[i].getCurrent()) {
                                if (knownDirection == pass[i].getDirection()) {
                                    if (checkElevatorWeight(elevatorType, pass[i].getWeight(), i) == 0) {
                                        //formatting 
                                        System.out.println("");

                                        //Print pickkup
                                        System.out.println("[" + elevatorContainer + "]: HAS PICKED UP " + pass[i].getID());

                                        //Add passenger to elevator container
                                        eRoute.put(pass[i].getID(), pass[i].getDestination());

                                        //Add passenger to elevator weight container
                                        passWeightCollection.put(pass[i].getID(), pass[i].getWeight());
//                                        //debug
//                                        System.out.println(pass[i].getID() + " WEIGHT: " + pass[i].getWeight());
//                                        System.out.println("ELEVATOR 1 WEIGHT: " + e1Weight);
//                                        System.out.println("ELEVATOR 2 WEIGHT: " + e2Weight);
                                        //get current total weight based on number of passengers and set to new weight
                                        float newTotalWeight = getNewTotalWeight(passWeightCollection);

                                        if (elevatorType == 1) {
                                            e1Weight = newTotalWeight;
                                        } else if (elevatorType == 2) {
                                            e2Weight = newTotalWeight;
                                        }

                                        pass[i] = null;
                                    } else {
                                        skippedWeightPassengers++;
                                        System.out.println("\n[" + elevatorContainer + "]: HAS SKIPPED " + pass[i].getID());
                                    }
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
    private void showElevatorPassenger(int elevatorType, String customFormat, Map<String, Integer> eRoute, Map<String, Float> passWeightCollection) {
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
        //System.out.println("[Map of " + elevatorContainer + " ]: " + passWeightCollection);

    }

    //Print all of the elevator operations
    private void printElevator1Operation(int floor, String customMessage) {
        printElevator(); //print elevator floor
        showElevatorPassenger(1, customMessage, e1Route, passWeight1Collection);       //print >    E1     <
        dropOffPerson(floor, 1, e1Route, passWeight1Collection, e1Weight);                       //drop person off
        commenceJourney(floor, 1, e1Route, e1KnownDirection, passWeight1Collection);  //makes the elevator check if the floor has passengers and picks up the person
    }

    //Print all of the elevator operations
    private void printElevator2Operation(int floor, String customMessage) {
        printElevator(); //print elevator floor
        showElevatorPassenger(2, customMessage, e2Route, passWeight2Collection);
        dropOffPerson(floor, 2, e2Route, passWeight2Collection, e2Weight); //drop person off
        commenceJourney(floor, 2, e2Route, e2KnownDirection, passWeight2Collection); //makes the elevator check if the floor has passengers and picks up the person
    }

    //Determine whether the elevator should go UP a floor or DOWN a floor
    private boolean checkPassengersAbove(boolean passengersAbove, int direction, int floor, Map<String, Integer> eRoute) {
        //If there are no more passengers, then go up to the last floor and end all operations
        if (pass.length == 0) {
            passengersAbove = true;
        }
        //If the elevator direction is not know, just go up so we can check each floor for passengers.
        if (direction == -1 && e1Route.isEmpty()) {
            passengersAbove = true;
        } else {
            //If the elevator direction is known, then elevator will go up or down if and only if the conditions have been met
            for (int i = 0; i < pass.length; i++) {
                if (pass[i] != null) {
                    //If there are any passengers whose direction is = 1 and the elevator is == 1, then floor++
                    if (direction == 1 && 1 == pass[i].getDirection() || e1Route.isEmpty()) {
                        passengersAbove = true;
                        //If the elevator is at the top of the floor but there is a passenger who has been skipped, then floor--;
                    }
                    if (floor >= 4 && (pass[i].getDirection() == 1)) {
                        passengersAbove = false;
                    }
                    if (floor <= 4 && floor >= pass[i].getCurrent()) {
                        passengersAbove = false;
                    }
                }
            }

        }
        if (floor == 5 && pass.length != 0) {
            passengersAbove = false;
        }
        if (!e1Route.isEmpty()) {
            passengersAbove = true;
        }

        return passengersAbove;
    }

    //Determine whether the elevator should go UP a floor or DOWN a floor
    private boolean checkPassengersAbove2(boolean passengersAbove, int direction, int floor, Map<String, Integer> eRoute) {
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
        while (floor <= 4 && floor > 0) {
            synchronized (this) {

                while (skippedPassengers > 0 || pass.length == 0) {
                    wait();
                }

                startNanoTime = System.nanoTime();

                //Set new total weight for the elevator based on the number of passengers inside the elevator
                float newTotalWeight = getNewTotalWeight(passWeight1Collection);
                e1Weight = newTotalWeight;

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

                if (floor == 4 && e1Route.isEmpty() && pass.length == 0) {
                    newTotalWeight = getNewTotalWeight(passWeight1Collection);
                    e1Weight = newTotalWeight;
                    System.out.println("\n[ELEVATOR 1 - FINISHED WEIGHT]: " + e1Weight);

                }
                
                notify();
                Thread.sleep(1);

            }
        }
        System.out.println("\n[ELEVATOR 1] FINISHED OPERATING! 🎉🎉🎉 ------------------------------------------------ ");


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
                //Set new total weight for the elevator based on the number of passengers inside the elevator
                float newTotalWeight = getNewTotalWeight(passWeight2Collection);
                e2Weight = newTotalWeight;

                //For each floor, print the floor, drop off any passengers (if any), pick up any passengers 
                //(if any), print the elevator with passengers (if any)
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
                passengersAbove = checkPassengersAbove2(passengersAbove, e2KnownDirection, floor, e2Route);

                if ((passengersAbove || e2KnownDirection == -1)) {
                    floor++;
                } else {
                    floor--;
                }
                if (floor == 5 && e2Route.isEmpty() && pass.length == 0) {
                    floor++;

                } else if (floor == 5 && !e2Route.isEmpty()) {
                    floor--;
                    e2KnownDirection = 2;
                    printElevator2Operation(floor, "%64s%10s%10s");

                } else if (floor == 1 && !e2Route.isEmpty()) {
                    printElevator2Operation(floor, "%s%10s%10s");
                    floor++;
                    e2KnownDirection = 1;

                }
                if(pass.length == 0 && e2Route.isEmpty()){
                    floor++;
                }
                notify();
                Thread.sleep(1);
            }
        }
        System.out.println("\n[ELEVATOR 2] FINISHED OPERATING! 🎉🎉🎉 ------------------------------------------------ ");

    }

}
