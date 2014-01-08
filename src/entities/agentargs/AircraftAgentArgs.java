package entities.agentargs;

import entities.Aircraft;
import entities.Airport;

/**
 * Represents the possible arguments for the AircraftAgent.
 */
public class AircraftAgentArgs implements IAgentArgs
{  
    private Aircraft aircraft;
    private Airport airport;

    public AircraftAgentArgs() { }
    
    public AircraftAgentArgs(Aircraft aircraft, Airport airport)
    {
        this.airport = airport;
        this.aircraft = aircraft;
    }
    
    public static AircraftAgentArgs createAgentArgs(Object[] args)
    {
        if(args == null)
            return null;
        
        if(args.length == 2)
            return new AircraftAgentArgs(args);

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
 
    @Override
    public Object[] asObjectArray() 
    {
        return new Object[] { airport, aircraft };
    }       
}
