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
    private String routeTime;
    private String aircraft;
    private String departureAirport;
    private String destinationAirport;
    private String cost;
    private String aircraftLocation;
    private String bookedSeats;
    private String availableSeats;
    
    public Stats(String time, String aircraft, String departureAirport, String destinationAirport, String cost, String aircraftLocation, String bookedSeats, String availableSeats) 
    {
        this.routeTime = time;
        this.aircraft = aircraft;
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.cost = cost;
        this.aircraftLocation = aircraftLocation;
        this.bookedSeats = bookedSeats;
        this.availableSeats = availableSeats;
    }

    public Stats(String csvString)
    {
        String[] args = csvString.split(",");
        this.routeTime = args[0];
        this.aircraft = args[1];
        this.departureAirport = args[2];
        this.destinationAirport = args[3];
        this.cost = args[4];
        this.aircraftLocation = args[5];
        this.bookedSeats = args[6];
        this.availableSeats = args[7];
    }
    
    public String toCsvString()
    {
        return routeTime + "," + aircraft + "," + departureAirport + "," + destinationAirport + "," + cost + "," + aircraftLocation + "," + bookedSeats + "," + availableSeats;
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

    public String getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(String overbookedSeats) {
        this.bookedSeats = overbookedSeats;
    }
    
    public String getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(String overbookedSeats) {
        this.availableSeats = overbookedSeats;
    }

    @Override
    public String toString() {
        return toCsvString();
    }
    
    
}
