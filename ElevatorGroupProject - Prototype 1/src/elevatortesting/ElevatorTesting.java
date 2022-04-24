/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package elevatortesting;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Asher
 */
public class ElevatorTesting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        LinkedList passenger1 = new LinkedList();
        LinkedList pass = new LinkedList();

        Person man = new Person("Michael");
        passenger1.add(man);
        int[] pFloorDestination = {0, 0, 0, 0};
        
        pFloorDestination[0] = 8;
        pFloorDestination[1] = 3;
        pFloorDestination[2] = 4;
        pFloorDestination[3] = 4;

        System.out.println("Size = " + pass.size());
        Arrays.sort(pFloorDestination);
        System.out.println(pFloorDestination[0] + "\n" + pFloorDestination[1]);
        
        System.out.println(0.00204 > 0.00012);
        
//        Person woman[] = new Person("test");
        
//        testing(man);
//        if(passenger1.contains(man)){
//            System.out.println("YES IT DOES CONTAIN A MAN!");
//        } else {
//            System.out.println("NOPE!");
//        }
//    
//        if (passenger1.getLast() == man) {
//            System.out.println("True");
//        } else {
//            System.out.println("False");
//        }

    }

//    public void testing(Object passenger) {
//        passenger.printName();
//    }
}

//backup
//for(int i = 0; i < passengerElevator1.size(); i++){
//                    if(passengerElevator1.get(i).contains(p1)){
//                        //If object does exist inside the linked list, we must first determine if this is the last person in the node of the linked list.
//                        
//                        if(passengerElevator1.get(i+1) != null){
//                            //Why do we need to do this? Because if the last person to join the elevator is the person who is causing the elevator to exceed 1000kg weight
//                            //then we simply remove the last person from the elevator.
//                            //But in order to check the last person, we simply have the pointer point to the next node in the list.
//                            
//                            //For example:
//                            /*
//                                [A][B][C][D]
//                                let's say that i=3 which is node D. Knowing that D is the last cell is not enough for Java to KNOW it is last value.
//                                i=4 means the value next to node D is nothing, therefore NULL.
//                            
//                            */
//                        }
//                        accumWeight += p1.returnWeight();
//                    }
//                }


            //next we need to check where the elevator is eventually going to go. The first person who pressed the button determins the direction of the elevator.
            //So we simply need to access each people's object and check the person's state.
            
            //Person Status Guide
//            if status=0 then the person is waiting for elevator but has not pressed the button.
//            If status=1 then person waiting for elevator, going up
//            If status=2 the person waiting for elevator, going down
//            If status=3 the person in the elevator
//            If status=4 the person is at the destination.