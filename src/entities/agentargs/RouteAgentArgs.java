/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.agentargs;

/**
 * Represents a container of arguments for the RouteAgent.
 * @author pla
 */
public class RouteAgentArgs implements IAgentArgs
{
    private int routeID, departureAirportID, destinationAirportID, numOfPassengers;
    
    /**
     * Constructs an empty instance of the RouteAgentArgs.
     * Please remember to initialize the public arguments of this instance.
     */
    public RouteAgentArgs() {}
    
    /**
     * Constructs an instance of this RouteAgentArgs, using the given parameters.
     * @param routeID The identifier of the route.
     * @param depAirportID The identifier of the departure airport.
     * @param destAirportID The identifier of the destination airport.
     * @param numOfPassengers Number of expected passengers (number of sold tickets).
     */
    public RouteAgentArgs(int routeID, int depAirportID, int destAirportID, int numOfPassengers)
    {
        this.routeID = routeID;
        this.departureAirportID = depAirportID;
        this.destinationAirportID = destAirportID;
        this.numOfPassengers = numOfPassengers;
    }
    
    /**
     * Constructs an instance of RouteAgentArgs using the given args.
     * @param args An array containing the arguments as objects.
     * 0) routeID, 1) departureAirportID, 2) destinationAirportID, 3) aircraftID, 4) numOfPassengers.
     * @return A RouteAgentArgs instance representing the arguments for the RouteAgent.
     */
    public static RouteAgentArgs createAgentArgs(Object[] args)
    {
        if(args == null)
            return null;
        
        if(args.length == 4)
        {
            return new RouteAgentArgs(args);
        }
        else if(args.length > 0)
        {
            RouteAgentArgs routeAgentArgs = new RouteAgentArgs();
            routeAgentArgs.routeID = (int)args[0];
            
            if(args.length >= 2)
                routeAgentArgs.departureAirportID = (int)args[1];
            if(args.length >= 3)
                routeAgentArgs.destinationAirportID = (int)args[2];
            
            return routeAgentArgs;
        }
        
        return null;
    }
    
    private RouteAgentArgs(Object[] args)
    {
        routeID = (int)args[0];
        departureAirportID = (int)args[1];
        destinationAirportID = (int)args[2];
        numOfPassengers = (int)args[3];
    }
    
    /**
     * Inserts the RouteAgent arguments into an array as objects.
     * 0) routeID, 1) departureAirportID, 2) destinationAirportID, 3) aircraftID, 4) numOfPassengers.
     * @return An array of objects representing the arguments of the RouteAgent.
     */
    @Override
    public Object[] asObjectArray() 
    {
        return new Object[] { routeID, departureAirportID, destinationAirportID, numOfPassengers };
    }
    
    public int getRouteID() { return routeID; }
    public int getDepartureAirportID() { return departureAirportID; }
    public int getDestinationAirportID() { return destinationAirportID; }
    public int getNumOfPassengers() { return numOfPassengers; }
    public void setRouteID(int routeID) { this.routeID = routeID; }
    public void setDepartureAirportID(int depAirportID) { departureAirportID = depAirportID; }
    public void setDestinationAirportID(int destAirportID) { destinationAirportID = destAirportID; }
    public void setNumOfPassengers(int numOfPassengers) { this.numOfPassengers = numOfPassengers; }
}
