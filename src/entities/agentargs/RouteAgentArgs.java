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
    public int routeID, departureAirportID, destinationAirportID, aircraftID, numOfPassengers;
    
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
     * @param acID The identifier of the aircraft.
     * @param numOfPassengers Number of expected passengers (number of sold tickets).
     */
    public RouteAgentArgs(int routeID, int depAirportID, int destAirportID, int acID, int numOfPassengers)
    {
        this.routeID = routeID;
        this.departureAirportID = depAirportID;
        this.destinationAirportID = destAirportID;
        this.aircraftID = acID;
        this.numOfPassengers = numOfPassengers;
    }
    
    /**
     * Constructs an instance of RouteAgentArgs using the given args.
     * @param args An array containing the arguments as objects.
     * 0) routeID, 1) departureAirportID, 2) destinationAirportID, 3) aircraftID, 4) numOfPassengers.
     */
    public RouteAgentArgs(Object[] args)
    {
        routeID = (int)args[0];
        departureAirportID = (int)args[1];
        destinationAirportID = (int)args[2];
        aircraftID = (int)args[3];
        numOfPassengers = (int)args[4];
    }
    
    /**
     * Inserts the RouteAgent arguments into an array as objects.
     * 0) routeID, 1) departureAirportID, 2) destinationAirportID, 3) aircraftID, 4) numOfPassengers.
     * @return An array of objects representing the arguments of the RouteAgent.
     */
    @Override
    public Object[] asObjectArray() 
    {
        return new Object[] { routeID, departureAirportID, destinationAirportID, aircraftID, numOfPassengers };
    }
}
