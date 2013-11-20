/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

/**
 *
 * @author Henrik
 */
public class Aircraft {
    
    private String tailnumber;
    private int aircraftID; 
    private int capacity; 
    private double speed; 
    private double fuelBurnRate; 

    public Aircraft(String tailnumber, int aircraftID, int capacity, double speed, double fuelBurnRate) {
        this.aircraftID = aircraftID;
        this.capacity = capacity;
        this.speed = speed;
        this.fuelBurnRate = fuelBurnRate;
        this.tailnumber = tailnumber;
    }

    public String getTailnumber() {
        return tailnumber;
    }

    public void setTailnumber(String tailnumber) {
        this.tailnumber = tailnumber;
    }

    public int getAircraftID() {
        return aircraftID;
    }

    public void setAircraftID(int aircraftID) {
        this.aircraftID = aircraftID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getFuelBurnRate() {
        return fuelBurnRate;
    }

    public void setFuelBurnRate(double fuelBurnRate) {
        this.fuelBurnRate = fuelBurnRate;
    }
    
}
