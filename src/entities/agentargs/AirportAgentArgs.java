/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.agentargs;

import entities.Airport;

/**
 * Represents a container of AirportAgent arguments.
 * @author pla
 */
public class AirportAgentArgs implements IAgentArgs
{

    public static AirportAgentArgs createAgentArgs(Object[] arguments) {

        return new AirportAgentArgs((Airport)arguments[0]);
        
    }

    private Airport airport;

    public AirportAgentArgs(Airport airport) {
        this.airport = airport;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
    
    
    
    @Override
    public Object[] asObjectArray() {

        return new Object[] { airport };
    
    }
  
    
    
}
