
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import Utils.*;
import jade.wrapper.ControllerException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        System.out.println(tryCreateAircraftAgents(NUMBER_OF_AGENTS_TO_CREATE) ? "Successfully created all" + NUMBER_OF_AGENTS_TO_CREATE+ " agents!" : "FAIL!"); // Test, do delete again.
    }
    
    protected void takeDown() 
    {
        System.out.println("shutting down main");
    }
    
    private boolean tryCreateAircraftAgents(int numberOfAgents)
    {
        try
        {
            AgentCreationMsgFactory fact = new AgentCreationMsgFactory(getAMS(), getContentManager());

            System.out.println("Creating " + numberOfAgents + " aircraft agents in the main container...");

            String agentClassName = "Agents.AircraftAgent";
            String containerName;

            try 
            {
                containerName = getContainerController().getContainerName();
            } 
            catch (ControllerException ex) 
            {
                containerName = "Main-Container";
            }

            for(int agents = 0; agents < numberOfAgents; agents++)
            {
                String agentName = "aircraftAgent" + agents;
                ACLMessage agentCreationMsg = fact.createRequestMessage(agentClassName, agentName, containerName);
                addBehaviour(createAgentCreationBehaviour(agentCreationMsg, agentClassName, agentName, containerName));
            }
            return true;
        }
        catch(Exception c)
        {
            return false;
        }
    }
    
    private Behaviour createAgentCreationBehaviour(ACLMessage request, String agentClass, String agentName, String containerName)
    {
        final String ac = agentClass;
        final String an = agentName;
        final String cn = containerName;
        
        return new AchieveREInitiator(this, request)
            {
                protected void handleInform(ACLMessage inform) 
                {
                    System.out.println("Successfully created a '" + ac + "' named '" + an 
                            + "' inside the following container: " + cn);
                }

                protected void handleFailure(ACLMessage failure) 
                {
                    System.out.println("Failed to create a '" + ac + "' named '" + an
                            + "' inside the following container: " + cn);
                }
            };
    }
}
