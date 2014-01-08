package entities.agentargs;

import entities.Airport;
import java.util.Date;

/**
 * Represents a container of arguments for the RouteAgent.
 */
public class RouteAgentArgs implements IAgentArgs
{
    private int routeID,  numOfPassengers;
    private Airport departureAirport, arrivalAirport;
    private Date earliestArrivalTime, latestArrivalTime;
    
    public RouteAgentArgs() {}

    public RouteAgentArgs(int routeID, int numOfPassengers, Airport departureAirport, Airport arrivalAirport, Date earliestArrivalTime, Date latestArrivalTime) {
        this.routeID = routeID;
        this.numOfPassengers = numOfPassengers;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.earliestArrivalTime = earliestArrivalTime;
        this.latestArrivalTime = latestArrivalTime;
    }
    
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
        departureAirport = (Airport)args[1];
        arrivalAirport = (Airport)args[2];
        numOfPassengers = (int)args[3];
        earliestArrivalTime = (Date)args[4];
        latestArrivalTime = (Date)args[5];
    }
    
    @Override
    public Object[] asObjectArray() 
    {
        return new Object[] { routeID, departureAirport, arrivalAirport, numOfPassengers, earliestArrivalTime, latestArrivalTime  };
    }
   
}
