package InfiniteElevator;

import org.jfree.ui.RefineryUtilities;



public class CircularJava {
    
    
    
 
    public static void main(String[] args)
            throws InterruptedException {

        BuildingFloor pc = new BuildingFloor();
        pc.ruleBuilding();
        pc.distributePeople();
 
        
        Elevator1 e1 = new Elevator1(pc);
        Elevator2 e2 = new Elevator2(pc);

        //Call threads to start
        e1.start();
        e2.start();

        //Join threads to start synchronization process
        e1.join();
        e2.join();
        
        //Generate Summary of Elevator System
        pc.generateSummary();
        
        System.out.println("\n\n----------------------------------------------------------------------------------------------------");
        System.out.println(" > END OF ELEVATOR OPERATIONS                                                          ");
        System.out.println("----------------------------------------------------------------------------------------------------\n");

    }
}
