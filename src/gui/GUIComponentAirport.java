/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.awt.Point;

/**
 *
 * @author Fuglsang
 */
public class GUIComponentAirport {
    private Point airportCoordinates;
    private String airportName; 
    
    public GUIComponentAirport(Point coordinates, String airportName) {
        airportCoordinates = coordinates; 
        this.airportName = airportName; 
    }
    
    public Point airportCoordinates() {
        return airportCoordinates; 
    }
    
    public String airportName() {
        return airportName; 
    }
}
