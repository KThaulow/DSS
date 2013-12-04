/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.cost;

import Utils.SphericalPositionCalculator;
import entities.SphericalPosition;

/**
 *
 * @author pla
 */
public class PassengerOptimizedCostModel2 implements ICostModel
{
    private int numOfPassengers;
    private int aircraftCapacity;
    private SphericalPosition currentAircraftPosition;
    private SphericalPosition departureAirportPosition;
    private SphericalPosition destinationAirportPosition;
    private double aircraftSpeed;
    private double fuelBurnRate;
    
    private final double NUM_OF_EMPTY_SEATS_FACTOR = 1000;
    private final double DIST_TO_DEPARTURE_AIRPORT_FACTOR = 11;
    private final double FUEL_CONSUMPTION_FACTOR = 9.8;
    private final int NUM_OF_DISC_PASSENGERS_FACTOR = 200;
    
    public PassengerOptimizedCostModel2(int numOfPassengers, int acCapacity, SphericalPosition currentAcPos, SphericalPosition depPos, SphericalPosition destPos, double acSpeed, double fuelBurnRate)
    {
        this.numOfPassengers = numOfPassengers;
        this.aircraftCapacity = acCapacity;
        this.aircraftSpeed = acSpeed;
        this.fuelBurnRate = fuelBurnRate;
        this.departureAirportPosition = depPos;
        this.currentAircraftPosition = currentAcPos;
        this.destinationAirportPosition = destPos;
    }
    
    @Override
    public double calculateCost()
    {
        int numOfEmptySeats = aircraftCapacity - numOfPassengers;
        int numOfDiscardedPassengers = 0;
        
        if(numOfEmptySeats < 0)
            numOfDiscardedPassengers = Math.abs(numOfEmptySeats) * NUM_OF_DISC_PASSENGERS_FACTOR;
        
        double distToDepAirport = 0;
        double distToDestAirport = 0;
        
        if(currentAircraftPosition != null && departureAirportPosition != null)
            distToDepAirport = SphericalPositionCalculator.INSTANCE.calculateDistance(currentAircraftPosition, departureAirportPosition);
        
        if(departureAirportPosition != null && destinationAirportPosition != null)
            distToDestAirport = SphericalPositionCalculator.INSTANCE.calculateDistance(departureAirportPosition, destinationAirportPosition);

        double totalDistance = Math.pow(distToDepAirport, 1.1) + distToDestAirport;
                
        double totalEET = totalDistance/aircraftSpeed;
        double fuelConsumption = fuelBurnRate * totalEET;
        
        double weightedNumOfEmptySeats = numOfEmptySeats * NUM_OF_EMPTY_SEATS_FACTOR;
        double weightedDistToDepAirport = distToDepAirport * DIST_TO_DEPARTURE_AIRPORT_FACTOR;
        double weightedFuelConsumption = fuelConsumption * FUEL_CONSUMPTION_FACTOR;
        double weightedDiscPassengers = Math.pow(numOfDiscardedPassengers, 2);
        
        long cost = (long)(weightedNumOfEmptySeats + weightedDistToDepAirport  + weightedFuelConsumption + weightedDiscPassengers);
        
        return cost > Double.MAX_VALUE ? Double.MAX_VALUE : cost;
    }
}
