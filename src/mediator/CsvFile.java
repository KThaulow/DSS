/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mediator;

import entities.Stats;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author pla
 */
public enum CsvFile 
{
    INSTANCE;
    
    private HashMap<Integer, Stats> stats;
    private File statsFile;
    
    private static final String ID = "ID";
    private static final String ROUTE_TIME = "Route Time";
    private static final String AIRCRAFT_NAME = "Aircraft";
    private static final String DEP_AIRPORT = "Departure Airport";
    private static final String DEST_AIRPORT = "Arrival Airport";
    private static final String COST = "Cost";
    private static final String CURRENT_LOCATION = "Current Location";
    private static final String OVERBOOKED_SEATS = "Overbooked Seats";
    
    private CsvFile() 
    {
        statsFile = new File("Stats.csv");
        stats = new HashMap<>();
    }
    
    public void addStats(Stats stats)
    {
        int id = getNextId();
        this.stats.put(id, stats);
    }
    
    public int getNextId()
    {
        return stats.size();
    }
    
    public void addStats(int id, Stats stats)
    {
        this.stats.put(id, stats);
    }
    
    public void write() throws IOException
    {
        FileWriter fileWriter = new FileWriter(statsFile, false);
        
        try
        {
            fileWriter.write(getCsvString());
        }
        catch(Exception c)
        {
            System.out.println("Unable to write CSV file.");
        }
        finally
        {
            fileWriter.close();
        }
    }
    
    private String getCsvString()
    {
        String content = getHeaderColumns() + "\n";
        
        for(Stats s : stats.values())
            content += s + "\n";
        
        return content;
    }
    
    private String getHeaderColumns()
    {
        return ID + "," + ROUTE_TIME + "," + AIRCRAFT_NAME + "," + DEP_AIRPORT + "," + DEST_AIRPORT + "," + COST + "," +CURRENT_LOCATION + "," + OVERBOOKED_SEATS;
    }
}
