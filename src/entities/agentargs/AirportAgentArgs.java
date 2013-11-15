/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.agentargs;

/**
 * Represents a container of AirportAgent arguments.
 * @author pla
 */
public class AirportAgentArgs implements IAgentArgs
{
    private int airportID, coordinateX, coordinateY;
    
    /**
     * Constructs an empty instance of Airport agent arguments.
     * Please remember to set the arguments manually.
     */
    public AirportAgentArgs() { }
    
    /**
     * Constructs an instance of airport agent arguments from the given parameters.
     * @param airportID The identifier of the airport.
     * @param coordinateX The X coordinate of the airport.
     * @param coordinateY The Y coordinate of the airport.
     */
    public AirportAgentArgs(int airportID, int coordinateX, int coordinateY)
    {
        this.airportID = airportID;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }
    
    /**
     * Constructs an instance of AirportAgentArgs using the given args.
     * @param args An array containing the arguments as objects.
     * 0) airportID, 1) coordinateX, 2) coordinateY.
     * @return An instance of AirportAgentArgs containing the arguments for the airport agent.
     */
    public static AirportAgentArgs createAgentArgs(Object[] args)
    {
        if(args == null)
            return null;
        
        if(args.length == 3)
        {
            return new AirportAgentArgs(args);
        }
        else if(args.length > 0)
        {
            AirportAgentArgs airportAgentArgs = new AirportAgentArgs();
            airportAgentArgs.airportID = (int)args[0];
            
            if(args.length >= 2)
                airportAgentArgs.coordinateX = (int)args[1];
            
            return airportAgentArgs;
        }
        
        return null;
    }
    
    private AirportAgentArgs(Object[] args)
    {
        airportID = (int)args[0];
        coordinateX = (int)args[1];
        coordinateY = (int)args[2];
    }
    
    /**
     * Converts the airport agent arguments to an array of objects.
     * 0) airportID, 1) coordinateX, 2) coordinateY.
     * @return An array of objects containing the arguments of the airport agent.
     */
    @Override
    public Object[] asObjectArray() 
    {
        return new Object[] { airportID, coordinateX, coordinateY };
    }
    
    public int getAirportID() { return airportID; }
    public int getCoordinateX() { return coordinateX; }
    public int getCoordinateY() { return coordinateY; }
    public void setAirportID(int airportID) { this.airportID = airportID; }
    public void setCoordinateX(int coordX) { this.coordinateX = coordX; }
    public void setCoordinateY(int coordY) { this.coordinateY = coordY; }
}
