/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import entities.Coord2D;

/**
 * Represents a singleton utility for calculating the current position of an aircraft.
 * @author pla
 */
public enum LinearCoordCalculator
{
    /**
     * Gets the singleton instance of the calculator.
     */
    INSTANCE;
    
    private LinearCoordCalculator(){}
    
    /**
     * Calculates and returns the coordinates of the aircraft, based on the
     * given distance travelled.
     * @param depCoord The coordinates of the departure airport.
     * @param destCoord The coordinates of the destination airport.
     * @param travelledDistance The distance travelled thus far.
     * @return The current coordinates of the aircraft. Returns null if either given coordinates are invalid.
     */
    public Coord2D getCoordinates(Coord2D depCoord, Coord2D destCoord, double travelledDistance)
    {
        if(depCoord.isValid() && destCoord.isValid())
        {
            if(depCoord.equals(destCoord))
                return depCoord;
            else if(isVertical(depCoord, destCoord))
                return calculateVerticalCoord(depCoord, destCoord, travelledDistance);
            else if(isHorizontal(depCoord, destCoord))
                return calculateHorizontalCoord(depCoord, destCoord, travelledDistance);

            return calculateCoordinates(depCoord, destCoord, travelledDistance);
        }
        
        return null;
    }
    
    /**
     * Calculates and returns the distance between two coordinates.
     * @param depCoord The departure coordinates.
     * @param destCoord The destination coordinates.
     * @return The distance between the two given coordinates.
     */
    public double calculateDistance(Coord2D depCoord, Coord2D destCoord)
    {
        double yDiff = destCoord.Y - depCoord.Y;
        double xDiff = destCoord.X - depCoord.X;
        
        double slope = yDiff / xDiff;
        double xDiffSquared = Math.pow(xDiff, 2);
        double yDiffSquared = Math.pow(yDiff, 2);
        
        return Math.sqrt(xDiffSquared + yDiffSquared);
    }
    
    private Coord2D calculateCoordinates(Coord2D depCoord, Coord2D destCoord, double travelledDistance)
    {
        double yDiff = destCoord.Y - depCoord.Y;
        double xDiff = destCoord.X - depCoord.X;
        
        double slope = yDiff / xDiff;
        double xDiffSquared = Math.pow(xDiff, 2);
        double yDiffSquared = Math.pow(yDiff, 2);
        
        double routeDistance = Math.sqrt(xDiffSquared + yDiffSquared); // Hypothenuse
        
        if(travelledDistance >= routeDistance)
            return destCoord;
        
        double yCoord = (yDiff / routeDistance) * travelledDistance + depCoord.Y;
        double xCoord = (yCoord - depCoord.Y)/slope + depCoord.X;
        
        return new Coord2D(xCoord, yCoord);
    }
    
    // In case of a vertical line...
    private Coord2D calculateVerticalCoord(Coord2D depCoord, Coord2D destCoord, double travelledDistance)
    {
        double routeDistance = Math.abs(destCoord.Y - depCoord.Y);
        
        if(travelledDistance > routeDistance)
           return destCoord;
        else if(depCoord.Y > destCoord.Y)
            return new Coord2D(depCoord.X, depCoord.Y - travelledDistance);
        
        return new Coord2D(depCoord.X, depCoord.Y + travelledDistance);
    }
    
    // In case of a horizontal line...
    private Coord2D calculateHorizontalCoord(Coord2D depCoord, Coord2D destCoord, double travelledDistance)
    {
        double routeDistance = Math.abs(destCoord.X - depCoord.X);
        
        if(travelledDistance > routeDistance)
            return destCoord;
        else if(depCoord.X > destCoord.X)
            return new Coord2D(depCoord.X - travelledDistance, depCoord.Y);
        
        return new Coord2D(depCoord.X + travelledDistance, depCoord.Y);
    }
    
    private boolean isVertical(Coord2D depCoord, Coord2D destCoord)
    {
        return depCoord.X == destCoord.X;
    }
    
    private boolean isHorizontal(Coord2D depCoord, Coord2D destCoord)
    {
        return depCoord.Y == destCoord.Y;
    }
}