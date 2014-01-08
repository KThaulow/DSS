package mediator;

import entities.Aircraft;
import java.util.HashMap;

public class AircraftManager {
    
    private HashMap<String, Aircraft> aircrafts;
    
    private AircraftManager() {
        aircrafts = new HashMap<>();
        aircrafts.put("KaspersFly", new Aircraft("KaspersFly", 1, 162, 450, 2400));
        aircrafts.put("PetersFly", new Aircraft("PetersFly", 2, 70, 250, 550));
        aircrafts.put("KristiansFly", new Aircraft("KristiansFly", 3, 148, 450, 2300));
        aircrafts.put("HenriksFly", new Aircraft("HenriksFly", 4, 265, 450, 4700));
        aircrafts.put("KaspersFly1", new Aircraft("KaspersFly1", 5, 72, 440, 1700));
        aircrafts.put("PetersFly1", new Aircraft("PetersFly1", 6, 162, 450, 2400));
        aircrafts.put("KristiansFly1", new Aircraft("KristiansFly1", 7, 70, 250, 550));
        aircrafts.put("HenriksFly1", new Aircraft("HenriksFly1", 8, 148, 450, 2300));
        aircrafts.put("KaspersFly2", new Aircraft("KaspersFly", 9, 265, 450, 4700));
        aircrafts.put("PetersFly2", new Aircraft("PetersFly", 10, 72, 440, 1700));
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
