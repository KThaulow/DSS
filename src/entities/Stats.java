/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

/**
 *
 * @author pla
 */
public class Stats 
{
    private int id;
    private String routeTime;
    private String aircraft;
    private String departureAirport;
    private String destinationAirport;
    private String cost;
    private String aircraftLocation;
    private String overbookedSeats;
    
    public Stats(int id, String time, String aircraft, String departureAirport, String destinationAirport, String cost, String aircraftLocation, String overbookedSeats) 
    {
        this.id = id;
        this.routeTime = time;
        this.aircraft = aircraft;
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.cost = cost;
        this.aircraftLocation = aircraftLocation;
        this.overbookedSeats = overbookedSeats;
    }

    public Stats(String csvString)
    {
        String[] args = csvString.split(",");
        this.id = Integer.parseInt(args[0]);
        this.routeTime = args[1];
        this.aircraft = args[2];
        this.departureAirport = args[3];
        this.destinationAirport = args[4];
        this.cost = args[5];
        this.aircraftLocation = args[6];
        this.overbookedSeats = args[7];
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String toCsvString()
    {
        return id + "," + routeTime + "," + aircraft + "," + departureAirport + "," + destinationAirport + "," + cost + "," + aircraftLocation + "," + overbookedSeats;
    }
    
    public String getRouteTime() {
        return routeTime;
    }

    public void setRouteTime(String routeTime) {
        this.routeTime = routeTime;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getAircraftLocation() {
        return aircraftLocation;
    }

    public void setAircraftLocation(String aircraftLocation) {
        this.aircraftLocation = aircraftLocation;
    }

    public String getOverbookedSeats() {
        return overbookedSeats;
    }

    public void setOverbookedSeats(String overbookedSeats) {
        this.overbookedSeats = overbookedSeats;
    }

    @Override
    public String toString() {
        return toCsvString();
    }
    
    
}
