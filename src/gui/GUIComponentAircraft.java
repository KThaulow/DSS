package GUI;

import java.awt.Point;

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
