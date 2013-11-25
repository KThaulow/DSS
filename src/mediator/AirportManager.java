/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mediator;

import entities.Airport;
import entities.SphericalPosition;
import java.util.HashMap;

/**
 *
 * @author Henrik
 */
public class AirportManager {
    
    private HashMap<String, Airport> airports;    
    
    private AirportManager() {
        airports = new HashMap<>();
        
        airports.put("EKCH", new Airport(new SphericalPosition(55.6136111111, 12.645), "Copenhagen", "EKCH", 1));
        airports.put("ESSA", new Airport(new SphericalPosition(59.6519012451, 17.9186000824), "Stockholm", "ESSA", 2));
        airports.put("ENGM", new Airport(new SphericalPosition(60.1941986084, 11.100399971), "Oslo", "ENGM", 3));
        airports.put("EDDF", new Airport(new SphericalPosition(50.0484008789, 8.57069969177), "Frankfurt", "EDDF", 4));
        airports.put("EGLL", new Airport(new SphericalPosition(51.4706001282, -0.461941003799), "Heathrow", "EGLL", 5));
    }
    
    public HashMap<String, Airport> getAllAirports() {
        return airports;
    
}

    public Airport getAirport(String icao) {       
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
