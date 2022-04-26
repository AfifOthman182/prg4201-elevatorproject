/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InfiniteElevator;

/**
 *
 * @author Asher
 */
public class GraphPassTimeTest {

    public String passengerCode;
    public int floorDest;
    public long timeTaken;

    GraphPassTimeTest(String passengerCode, int floorDest, long timeTaken) {
        this.passengerCode = passengerCode;
        this.floorDest = floorDest;
        this.timeTaken = timeTaken;
    }

    
    public long getTime(){
        return timeTaken;
    }
    
    public String getPassengerCode(){
        return passengerCode;
    }
            
}
