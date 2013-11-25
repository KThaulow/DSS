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
        
        double dist = travelledDist/EARTH_RADIUS;
        double brng = toRad(getBearing(depPos, destPos));
        double lat1 = toRad(depPos.getLatitude());
        double lon1 = toRad(depPos.getLongitude());

        double lat2 = Math.asin(Math.sin(lat1) * Math.cos(dist) + Math.cos(lat1) * Math.sin(dist) * Math.cos(brng));
        
        double lon2 = lon1 + Math.atan2(Math.sin(brng) * Math.sin(dist) * Math.cos(lat1), 
                                     Math.cos(dist) - Math.sin(lat1) * Math.sin(lat2));
        
        lon2 = (lon2 + 3*Math.PI) % (2*Math.PI) - Math.PI;

        return new SphericalPosition(toDeg(lat2), toDeg(lon2));
    }
    
    public double calculateDistance(SphericalPosition depPos, SphericalPosition destPos)
    {
        double dLat = toRad(destPos.getLatitude()) - toRad(depPos.getLatitude());
        double dLon = toRad(destPos.getLongitude()) - toRad(depPos.getLongitude());
        double lat1 = toRad(depPos.getLatitude());
        double lat2 = toRad(destPos.getLatitude());
        
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        
        return EARTH_RADIUS * c;
    }
    
    private double getBearing(SphericalPosition depPos, SphericalPosition destPos)
    {
        double lat1 = toRad(depPos.getLatitude());
        double lat2 = toRad(destPos.getLatitude());
        double dLon = toRad(destPos.getLongitude() - depPos.getLongitude());
        
        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1)*Math.sin(lat2) - Math.sin(lat1)*Math.cos(lat2)*Math.cos(dLon);
        double brng = Math.atan2(y, x);

        return (toDeg(brng) + 360) % 360;
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
