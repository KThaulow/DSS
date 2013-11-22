/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 *
 * @author Fuglsang
 */
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
