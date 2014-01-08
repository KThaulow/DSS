package GUI;

import java.awt.geom.Point2D;

public class GUIComponentAirport {
    private Point2D.Double airportCoordinates;
    private String airportName; 
    
    public GUIComponentAirport(Point2D.Double coordinates, String airportName) {
        airportCoordinates = coordinates; 
        this.airportName = airportName; 
    }
    
    public Point2D.Double airportCoordinates() {
        return airportCoordinates; 
    }
    
    public String airportName() {
        return airportName; 
    }
}
