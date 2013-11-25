/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

/**
 *
 * @author Henrik
 */
public class Airport {
    
    private SphericalPosition location;
    private String name;
    private String icao;
    private int id;

    public Airport(SphericalPosition location, String name, String icao, int id) {
        this.location = location;
        this.name = name;
        this.icao = icao;
        this.id = id;
    }   
    
    /**
     * @return the location
     */
    public SphericalPosition getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Airport{" + "name=" + name + ", icao=" + icao + '}';
    }
    
    /**
     * @param location the location to set
     */
    public void setLocation(SphericalPosition location) {
        this.location = location;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the icao
     */
    public String getIcao() {
        return icao;
    }

    /**
     * @param icao the icao to set
     */
    public void setIcao(String icao) {
        this.icao = icao;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
