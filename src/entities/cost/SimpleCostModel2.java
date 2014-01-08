package entities.cost;

import Utils.SphericalPositionCalculator;
import entities.SphericalPosition;

public class SimpleCostModel2 implements ICostModel
{
    private int numOfPassengers;
    private int aircraftCapacity;
    private SphericalPosition currentAircraftPosition;
    private SphericalPosition departureAirportPosition;
    private SphericalPosition destinationAirportPosition;
    private double aircraftSpeed;
    private double fuelBurnRate;
    
    private final double NUM_OF_EMPTY_SEATS_FACTOR = 2;
    private final double DIST_TO_DEPARTURE_AIRPORT_FACTOR = 11;
    private final double FUEL_CONSUMPTION_FACTOR = 9.8;
    
    /**
     * Constructs a very simple proof-of-concept cost model.
     * @param numOfPassengers The number of passengers (sold tickets)
     * @param acCapacity The total number of seats in the aircraft.
     * @param currentAcPos To current position of the aircraft.
     * @param depPos The coordinates of the departure airport.
     * @param destPos The coordinates of the destination airport.
     * @param acSpeed The speed of the aircraft. 
     * @param fuelBurnRate Fuel consumption per time unit.
     */
    public SimpleCostModel2(int numOfPassengers, int acCapacity, SphericalPosition currentAcPos, SphericalPosition depPos, SphericalPosition destPos, double acSpeed, double fuelBurnRate)
    {
        this.numOfPassengers = numOfPassengers;
        this.aircraftCapacity = acCapacity;
        this.aircraftSpeed = acSpeed;
        this.fuelBurnRate = fuelBurnRate;
        this.departureAirportPosition = depPos;
        this.currentAircraftPosition = currentAcPos;
        this.destinationAirportPosition = destPos;
    }
    
    /**
     * Calculates the cost of using this aircraft, based on the number of passengers,
     * empty seats, distance to the departure airport, estimated time of flight and fuel consumption.
     * @return The cost of using this aircraft.
     */
    @Override
    public double calculateCost()
    {
        int numOfEmptySeats = aircraftCapacity - numOfPassengers;
        double distToDepAirport = 0;
        double distToDestAirport = 0;
        
        if(currentAircraftPosition != null && departureAirportPosition != null)
            distToDepAirport = SphericalPositionCalculator.INSTANCE.calculateDistance(currentAircraftPosition, departureAirportPosition);
        
        if(departureAirportPosition != null && destinationAirportPosition != null)
            distToDestAirport = SphericalPositionCalculator.INSTANCE.calculateDistance(departureAirportPosition, destinationAirportPosition);

        double totalDistance = Math.pow(distToDepAirport, 1.2) + distToDestAirport;
                
        double totalEET = totalDistance/aircraftSpeed;
        double fuelConsumption = fuelBurnRate * totalEET;
        
        double weightedNumOfEmptySeats = numOfEmptySeats * NUM_OF_EMPTY_SEATS_FACTOR;
        double weightedDistToDepAirport = distToDepAirport * DIST_TO_DEPARTURE_AIRPORT_FACTOR;
        double weightedFuelConsumption = fuelConsumption * FUEL_CONSUMPTION_FACTOR;
        
        return weightedNumOfEmptySeats + weightedDistToDepAirport  + weightedFuelConsumption;
    }
}
