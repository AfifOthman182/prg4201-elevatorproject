/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InfiniteElevator;

/**
 *
 * @author Asher
 */
public class Passenger {
    
    private String id;
    
    private float weight;

    private int destination;

    private int current;

    private int direction;
    
    
    
    public Passenger(String id, float weight, int destination, int current, int direction){
        this.id = id;
        this.weight = weight;
        this.destination = destination;
        this.current = current;
        this.direction = direction;
    }
    
    public int getCurrent(){
        return current;
    }
    
    public String getID(){
        return id;
    }
    
    public int getDirection(){
        return direction;
    }
    
    public int getDestination() {
        return destination;
    }
    
    
    public void setCurrent(int current){
        this.current = current;
    }
}
