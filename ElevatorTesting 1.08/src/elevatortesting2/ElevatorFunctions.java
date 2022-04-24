/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package elevatortesting2;

import java.util.LinkedList;

/**
 *
 * @author Asher
 */
public class ElevatorFunctions {

    LinkedList playerDestinationFlrList = new LinkedList();
    int x;

    public synchronized void getPersonCurrentFloor(int personCurrentFloor) {

        playerDestinationFlrList.add(personCurrentFloor);
        System.out.println("TEST: " + playerDestinationFlrList);
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Thread interrupted.");

        }
        
        x += personCurrentFloor;
        System.out.println("TEST SYNC: " + x);
    }
}
