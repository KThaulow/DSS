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
import java.util.ArrayList;

/**
 *
 * @author pla
 */
public class CsvFile 
{
    private ArrayList<Stats> statsEntries;
    private File statsFile;
    
    private static final String ROUTE_TIME = "Route Time";
    private static final String AIRCRAFT_NAME = "Aircraft";
    private static final String DEP_AIRPORT = "Departure Airport";
    private static final String DEST_AIRPORT = "Arrival Airport";
    private static final String COST = "Cost";
    private static final String CURRENT_LOCATION = "Current Location";
    private static final String OVERBOOKED_SEATS = "Overbooked Seats";
    
    public CsvFile(String filePath) 
    {
        statsFile = new File(filePath);
        statsEntries = new ArrayList<>();
    }
    
    public void addStats(Stats stats)
    {
        statsEntries.add(stats);
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
    
    public String getNameWithoutExtension()
    {
        String fileName = statsFile.getName();
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
    
    private String getCsvString()
    {
        String content = getHeaderColumns() + "\n";
        
        for(Stats s : statsEntries)
            content += s + "\n";
        
        return content;
    }
    
    private String getHeaderColumns()
    {
        return ROUTE_TIME + "," + AIRCRAFT_NAME + "," + DEP_AIRPORT + "," + DEST_AIRPORT + "," + COST + "," +CURRENT_LOCATION + "," + OVERBOOKED_SEATS;
    }
}
