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
 * Represents a behaviour for creating and registering one or more new agents.
 * @author pla
 */
public class GenericAgentCreationBehaviour extends OneShotBehaviour
{
    private String containerName;
    private String agentClassName;
    private String agentName;
    private int numOfAgents;
    private String args[];
    
    /**
     * Constructs an agent creation behaviour, for the registration of multiple agents.
     * @param numOfAgents The number of agents to be created.
     * @param containerName The container name in which the agents are to be registered.
     * @param agentClassName The class name of the agents to be created package.class.
     * @param agentName The name of the agents to be created.
     * @param args The arguments for the agents 
     */
    public GenericAgentCreationBehaviour(int numOfAgents, String containerName, String agentClassName, String agentName, String args[])
    {
        this.numOfAgents = numOfAgents;
        this.containerName = containerName;
        this.agentClassName = agentClassName;
        this.agentName = agentName;
        this.args = args;
    }
    
    /**
     * Constructs an agent creation behaviour, for the registration of one agent.
     * @param containerName The container name in which the agent is to be registered.
     * @param agentClassName The class name of the agent to be created package.class.
     * @param agentName The name of the agent to be created.
     * @param args The arguments for the agent 
     */
    public GenericAgentCreationBehaviour(String containerName, String agentClassName, String agentName, String args[])
    {
        this.numOfAgents = 1;
        this.containerName = containerName;
        this.agentClassName = agentClassName;
        this.agentName = agentName;
        this.args = args;
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
