/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mediator;

import entities.Aircraft;
import java.util.HashMap;

/**
 *
 * @author Henrik
 */
public class AircraftManager {
    
    private HashMap<String, Aircraft> aircrafts;
    
    private AircraftManager() {
        aircrafts = new HashMap<>();
        aircrafts.put("KaspersFly", new Aircraft("KaspersFly", 1, 150, 458, 1500));
        aircrafts.put("PetersFly", new Aircraft("PetersFly", 2, 125, 400, 1200));
        aircrafts.put("KristiansFly", new Aircraft("KristiansFly", 3, 250, 380, 3000));
        aircrafts.put("HenriksFly", new Aircraft("HenriksFly", 4, 75, 300, 700));
    }
    
    public Aircraft getAircraft(String tailnumber) {
        return aircrafts.get(tailnumber);
    }
    
    public static AircraftManager getInstance() {
        if(instance == null)
            instance = new AircraftManager();
        
        return instance;
    }
    
    private static AircraftManager instance;
    
}
