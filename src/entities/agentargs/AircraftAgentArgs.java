/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.agentargs;

/**
 * Represents the possible arguments for the AircraftAgent.
 * @author pla
 */
public class AircraftAgentArgs implements IAgentArgs
{
    private int aircraftID, capacity, airportID;
    private double speed, fuelBurnRate;

    /**
     * Constructs an empty instance of aircraft agent arguments.
     * Please remember to set the arguments manually.
     */
    public AircraftAgentArgs() { }
    
    /**
     * Constructs an instance of aircraft agent arguments from the given parameters.
     * @param aircraftID The ID of the aircraft.
     * @param capacity The capacity of the aircraft.
     * @param speed The speed of the aircraft.
     * @param fuelBurnRate The rate of fuel consumption.
     * @param airportID The ID of the starting airport.
     */
    public AircraftAgentArgs(int aircraftID, int capacity, double speed, double fuelBurnRate, int airportID)
    {
        this.aircraftID = aircraftID;
        this.capacity = capacity;
        this.speed = speed;
        this.fuelBurnRate = fuelBurnRate;
        this.airportID = airportID;
    }
    
    /**
     * Constructs an instance of AircraftAgentArgs using the given args.
     * @param args An array containing the arguments as objects.
     * 0) aircraftID, 1) capacity, 2) speed, 3) fuelBurnRate.
     * @return An instance of AircraftAgentArgs representing arguments for the aircraft agent.
     */
    public static AircraftAgentArgs createAgentArgs(Object[] args)
    {
        if(args == null)
            return null;
        
        if(args.length == 5)
        {
            return new AircraftAgentArgs(args);
        }
        else if(args.length > 0)
        {
            AircraftAgentArgs acAgentArgs = new AircraftAgentArgs();
            acAgentArgs.aircraftID = (int)args[0];
            
            if(args.length >= 2)
                acAgentArgs.capacity = (int)args[1];
            if(args.length >= 3)
                acAgentArgs.speed = (double)args[2];
            if(args.length >= 4)
                acAgentArgs.fuelBurnRate = (double)args[3];
            
            return acAgentArgs;
        }
        
        return null;
    }
    
    private AircraftAgentArgs(Object[] args)
    {
        aircraftID = (int)args[0];
        capacity = (int)args[1];
        speed = (double)args[2];
        fuelBurnRate = (double)args[3];
        airportID = (int)args[4];
    }
    
    /**
     * Inserts the arguments into an array of objects.
     * 0) aircraftID, 1) capacity, 2) speed, 3) fuelBurnRate, 4) airportID.
     * @return An object array representation of the aircraft agent's arguments.
     */
    @Override
    public Object[] asObjectArray() 
    {
        return new Object[] { aircraftID, capacity, speed, fuelBurnRate };
    }
    
    public int getAircraftID() { return aircraftID; }
    public int getCapacity() { return capacity; }
    public double getSpeed() { return speed; }
    public double getFuelBurnRate() { return fuelBurnRate; }
    public int getAirportID() { return airportID; }
    public void setAircraftID(int acID) { aircraftID = acID; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setSpeed(double speed) { this.speed = speed; }
    public void setFuelBurnRate(double fuelBurnRate) { this.fuelBurnRate = fuelBurnRate; }
    public void setAirportID(int airportID) { this.airportID = airportID; }
}
