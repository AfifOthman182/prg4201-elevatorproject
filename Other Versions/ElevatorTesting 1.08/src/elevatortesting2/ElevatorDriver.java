/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package elevatortesting2;

/**
 *
 * @author Asher
 */
public class ElevatorDriver {

    
        public static void main(String[] args){
            
            //STARTING CONFIGURATION
            System.out.println("-------------------------- STARTING CONFIGURATION --------------------------");
            System.out.println("[]: Elevator is on floor 3 ");
            System.out.println("[]: James is on floor 1, Destination is on floor 1 ");
            System.out.println("[]: Michael is on floor 2 ");
            System.out.println("-----------------------------------------------------------------------------");

            
            ElevatorMutex elevatorFunction = new ElevatorMutex();

            //Person p = new Person(id,  'destination floor', 'current floor', 'state', 'mutex', name)
            //Moving same floor
//            PersonThread1 p = new PersonThread1(1, 1, 1, elevatorFunction, "James");
//            //Moving up
//            PersonThread1 p1 = new PersonThread1(4, 1, 1, elevatorFunction, "Michael");
            //Moving down
            PersonThread1 p2 = new PersonThread1(4, 1, 0, elevatorFunction, "Jacob");
            PersonThread1 p3 = new PersonThread1(4, 1, 0, elevatorFunction, "Jeb");

            
            //p.start();
            //p1.start();
            p2.start();
            p3.start();
        }

}
