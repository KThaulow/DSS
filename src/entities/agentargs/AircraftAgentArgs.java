/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.agentargs;

import entities.Aircraft;
import entities.Airport;

/**
 * Represents the possible arguments for the AircraftAgent.
 * @author pla
 */
public class AircraftAgentArgs implements IAgentArgs
{
  
    private Aircraft aircraft;
    private Airport airport;

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
    public AircraftAgentArgs(Aircraft aircraft, Airport airport)
    {
        this.airport = airport;
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
        
        if(args.length == 2)
        {
            return new AircraftAgentArgs(args);
        }

        
        return null;
    }
    
    private AircraftAgentArgs(Object[] args)
    {
        airport = (Airport) args[0];
        aircraft = (Aircraft) args[1];
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
    
    /**
     * Inserts the arguments into an array of objects.
     * 0) aircraftID, 1) capacity, 2) speed, 3) fuelBurnRate, 4) airportID.
     * @return An object array representation of the aircraft agent's arguments.
     */
    @Override
    public Object[] asObjectArray() 
    {
        return new Object[] { airport, aircraft };
    }
 
        
}
