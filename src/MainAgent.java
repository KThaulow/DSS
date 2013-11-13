
import jade.core.Agent;
import AgentBehaviours.*;

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
    private static final int NUMBER_OF_AGENTS_TO_CREATE = 10;
    
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
        
        addBehaviour(new GenericAgentCreationBehaviour(NUMBER_OF_AGENTS_TO_CREATE, containerName, "Agents.AircraftAgent", "acAgent"));
    }
    
    protected void takeDown() 
    {
        System.out.println("shutting down main");
    }
}
