/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import entities.SphericalPosition;

/**
 *
 * @author pla
 */
public enum SphericalPositionCalculator 
{
    INSTANCE;
    
    private static final double EARTH_RADIUS = 3440.06; // nautical miles
    
    private SphericalPositionCalculator() {}
    
    public SphericalPosition getPosition(SphericalPosition depPos, SphericalPosition destPos, double travelledDist)
    {
        double distToDest = calculateDistance(depPos, destPos);
        
        if(distToDest <= travelledDist)
            return destPos;
        
        double bearing = getBearing(depPos, destPos);
        
        double lat2 = Math.asin(Math.sin(depPos.getLatitude()) * Math.cos(travelledDist / EARTH_RADIUS) 
                + Math.cos(depPos.getLatitude()) * Math.sin(travelledDist / EARTH_RADIUS) * Math.cos(bearing));
        
        double lon2 = depPos.getLongitude() + Math.atan2(Math.sin(bearing) * Math.sin(travelledDist / EARTH_RADIUS) 
                * Math.cos(depPos.getLatitude()), Math.cos(travelledDist / EARTH_RADIUS) - Math.sin(depPos.getLatitude()) * Math.sin(lat2));

        return new SphericalPosition(lat2, lon2);
    }
    
    public double calculateDistance(SphericalPosition depPos, SphericalPosition destPos)
    {
        double dLat = toRad(destPos.getLatitude() - depPos.getLatitude());
        double dLon = toRad(destPos.getLongitude() - depPos.getLongitude());
        double lat1 = toRad(depPos.getLatitude());
        double lat2 = toRad(destPos.getLatitude());
        
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        
        return EARTH_RADIUS * c;
    }
    
    private double getBearing(SphericalPosition depPos, SphericalPosition destPos)
    {
        double dLat = toRad(destPos.getLatitude() - depPos.getLatitude());
        double dLon = toRad(destPos.getLongitude() - depPos.getLongitude());
        
        double y = Math.sin(dLon) * Math.cos(destPos.getLatitude());
        double x = Math.cos(depPos.getLatitude()) * Math.sin(destPos.getLatitude()) 
                    - Math.sin(depPos.getLatitude()) * Math.cos(destPos.getLatitude()) * Math.cos(dLon);
        
        double brng = toDeg(Math.atan2(y, x));
        
        return brng;
    }
    
    private double toDeg(double val)
    {
        return val * 180 / Math.PI;
    }
    
    private double toRad(double val)
    {
        return val * Math.PI / 180;
    }
}
