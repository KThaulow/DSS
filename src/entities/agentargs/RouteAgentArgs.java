/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.agentargs;

import entities.Airport;
import java.util.Date;
import javax.print.attribute.standard.NumberOfDocuments;

/**
 * Represents a container of arguments for the RouteAgent.
 * @author pla
 */
public class RouteAgentArgs implements IAgentArgs
{
    private int routeID,  numOfPassengers;
    private Airport departureAirport, arrivalAirport;
    private Date earliestArrivalTime, latestArrivalTime;
    
    
    /**
     * Constructs an empty instance of the RouteAgentArgs.
     * Please remember to initialize the public arguments of this instance.
     */
    public RouteAgentArgs() {}

    public RouteAgentArgs(int routeID, int numOfPassengers, Airport departureAirport, Airport arrivalAirport, Date earliestArrivalTime, Date latestArrivalTime) {
        this.routeID = routeID;
        this.numOfPassengers = numOfPassengers;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.earliestArrivalTime = earliestArrivalTime;
        this.latestArrivalTime = latestArrivalTime;
    }
    
    /**
     * Constructs an instance of RouteAgentArgs using the given args.
     * @param args An array containing the arguments as objects.
     * 0) routeID, 1) departureAirportID, 2) destinationAirportID, 3) aircraftID, 4) numOfPassengers.
     * @return A RouteAgentArgs instance representing the arguments for the RouteAgent.
     */
    public static RouteAgentArgs createAgentArgs(Object[] args)
    {
        
        if(args.length == 6)
        {
            return new RouteAgentArgs(args);
        }
        
        return null;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public int getNumOfPassengers() {
        return numOfPassengers;
    }

    public void setNumOfPassengers(int numOfPassengers) {
        this.numOfPassengers = numOfPassengers;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Date getEarliestArrivalTime() {
        return earliestArrivalTime;
    }

    public void setEarliestArrivalTime(Date earliestArrivalTime) {
        this.earliestArrivalTime = earliestArrivalTime;
    }

    public Date getLatestArrivalTime() {
        return latestArrivalTime;
    }

    public void setLatestArrivalTime(Date latestArrivalTime) {
        this.latestArrivalTime = latestArrivalTime;
    }
    
    
    
    private RouteAgentArgs(Object[] args)
    {
        routeID = (int)args[0];

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
        return new Object[] { routeID, departureAirport, arrivalAirport, numOfPassengers, earliestArrivalTime, latestArrivalTime  };
    }
   
}
