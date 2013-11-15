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
public class GUIComponentAircraft {
    private final Point aircraftCoordinates; 
    private final int capacity; 
    private final double speed; 
    
    public GUIComponentAircraft(Point coordinates, int aircraftCapacity, double aircraftSpeed) {
        aircraftCoordinates = coordinates; 
        capacity = aircraftCapacity; 
        speed = aircraftSpeed; 
    }
    
    public Point airCraftCoordinates() {
        return aircraftCoordinates; 
    }
    
    public int capacity() {
        return capacity; 
    }
    
    public double speed() {
        return speed; 
    }
    
}
