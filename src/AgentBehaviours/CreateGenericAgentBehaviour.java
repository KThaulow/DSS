/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AgentBehaviours;

import Utils.AgentCreationMsgFactory;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

/**
 *
 * @author pla
 */
public class CreateGenericAgentBehaviour extends OneShotBehaviour
{
    private String containerName;
    private String agentClassName;
    private String agentName;
    private int numOfAgents;
    
    public CreateGenericAgentBehaviour(int numOfAgents, String containerName, String agentClassName, String agentName)
    {
        this.numOfAgents = numOfAgents;
        this.containerName = containerName;
        this.agentClassName = agentClassName;
        this.agentName = agentName;
    }
    
    @Override
    public void action() 
    {
        AgentCreationMsgFactory fact = new AgentCreationMsgFactory(myAgent.getAMS(), myAgent.getContentManager());

        System.out.println("Creating " + numOfAgents + " aircraft agents in the main container...");

        for(int agents = 0; agents < numOfAgents; agents++)
        {
            String indexedAgentName = "aircraftAgent" + agents;
            ACLMessage agentCreationMsg = fact.createRequestMessage(agentClassName, indexedAgentName, containerName);
            myAgent.addBehaviour(createAgentCreationBehaviour(agentCreationMsg, indexedAgentName));
        }
    }
    
    private Behaviour createAgentCreationBehaviour(ACLMessage request, String specificAgentName)
    {
        final String sAN = specificAgentName;
        
        return new AchieveREInitiator(myAgent, request)
            {
                protected void handleInform(ACLMessage inform) 
                {
                    System.out.println("Successfully created a '" + agentClassName + "' named '" + sAN 
                            + "' inside the following container: " + containerName);
                }

                protected void handleFailure(ACLMessage failure) 
                {
                    System.out.println("Failed to create a '" + agentClassName + "' named '" + sAN
                            + "' inside the following container: " + containerName);
                }
            };
    }
}
