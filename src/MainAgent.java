
import jade.core.Agent;
import AgentBehaviours.*;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pla
 */
public class MainAgent extends Agent
{
    private static final int NUMBER_OF_AIRCRAFT_AGENTS = 10;
    private static final int NUMBER_OF_AIRPORT_AGENTS = 3;
    
    protected void setup() 
    {
        System.out.println("Successfully initialized main agent.");
        
        String containerName;
        
        try
        {
            containerName = getContainerController().getContainerName();
        }
        catch(Exception c)
        {
            containerName = "Main-Container";
        }

        setupAllAgents();
    }
    
    /***
     * Creates and sets up all the agents
     */
    private void setupAllAgents(){
        String aircraftArguments[] = {};
        for(int i=0; i<NUMBER_OF_AIRCRAFT_AGENTS; i++){
            createAgent("acAgent", "Agents.AircraftAgent", aircraftArguments);
        }
        
        String airportArguments[][] = {{"1","2","3"}, {"2","5","6"}, {"3","8","10"}};
        for(int i=0; i<NUMBER_OF_AIRPORT_AGENTS; i++){
            createAgent("acAgent", "Agents.AircraftAgent", airportArguments[i]);
        }
    }
    
    /**
     * Creates an agent safely
     * @param agentName Name of the agent
     * @param className Class name of the agent
     * @param args Arguments of the agent
     */
    private void createAgent(String agentName,String className,Object[] args)
    {
        try {
            setUpAgent(agentName,className,args);
        }
        catch (StaleProxyException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Sets up an agent
     * @param agentName Name of the agent
     * @param className Class name of the agent
     * @param args Arguments for the agent
     * @throws StaleProxyException 
     */
    private void setUpAgent(String agentName,String className,Object[] args) throws StaleProxyException 
    {
        ((AgentController)getContainerController().createNewAgent(agentName,className,args)).start();
    }
    
    protected void takeDown() 
    {
        System.out.println("shutting down main");
    }
}
