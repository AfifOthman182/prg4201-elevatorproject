/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InfiniteElevator;

/**
 *
 * @author Asher
 */
public class Elevator2 extends Thread {

    BuildingFloor bf = new BuildingFloor();

    public Elevator2(BuildingFloor pc) {
        this.bf = pc;
    }

    @Override
    public void run() {
        try {
            bf.elevator2();
        } catch (InterruptedException e) {
        }
    }
}
