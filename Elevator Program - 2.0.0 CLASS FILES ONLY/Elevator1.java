/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InfiniteElevator;

/**
 *
 * @author Asher
 */
public class Elevator1 extends Thread {

    BuildingFloor bf = new BuildingFloor();
    
    public Elevator1(BuildingFloor pc){
        this.bf = pc;
    }

    @Override
    public void run() {
        try {
            bf.elevator1();
        } catch (InterruptedException e) {
        }
    }

}
