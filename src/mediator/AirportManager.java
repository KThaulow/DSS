/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mediator;

import entities.Airport;
import entities.Coord2D;
import java.util.HashMap;

/**
 *
 * @author Henrik
 */
public class AirportManager {
    
    private HashMap<String, Airport> airports;    
    
    private AirportManager() {
        
        airports = new HashMap<>();
        
        airports.put("EKCH", new Airport(new Coord2D(55, 12), "Copenhagen", "EKCH", 1));
        airports.put("ESSA", new Airport(new Coord2D(59, 17), "Stockholm", "ESSA", 2));
        airports.put("ENGM", new Airport(new Coord2D(60, 11), "Oslo", "ENGM", 3));
        airports.put("EDDF", new Airport(new Coord2D(50, 8), "Frankfurt", "EDDF", 4));
        airports.put("EGLL", new Airport(new Coord2D(51, 27), "Heathrow", "EGLL", 5));

    }

    public Airport getAirprot(String icao) {       
        return airports.get(icao);
    }    
    
    public static AirportManager getInstance() {
        
        if(instance == null) {
            instance = new AirportManager();
        }
        
        return instance;
    }
    
    private static AirportManager instance;
    
    
}
