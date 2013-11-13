/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.ContainerID;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.CreateAgent;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author pla
 */
public class AgentCreationMsgFactory 
{
    private AID ams;
    private ContentManager cm;
    
    public AgentCreationMsgFactory(AID ams, ContentManager cm)
    {
        this.ams = ams;
        this.cm = cm;
        
        registerJadeMgrOntologyIfNotExists(this.cm); // Required, else an unknown ontology exception is thrown
        registerFipaSlLangIfNotExists(this.cm); // Required, else an unknown language/codec exception is thrown
    }
    
    /**
     * Attempts to create and register a new agent, using the given parameters.
     * @param agentClass the class of the agent package.agent.
     * @param agentName the name of the agent to be registered.
     * @param containerName the container in which the agent is to be registered.
     */
    public ACLMessage createRequestMessage(String agentClass, String agentName, String containerName)
    {
        CreateAgent ca = new CreateAgent();
        ca.setAgentName(agentName);
        ca.setClassName(agentClass);
        ca.setContainer(new ContainerID(containerName, null));

        Action a = new Action(ams, ca);
        
        ACLMessage requestMsg = new ACLMessage(ACLMessage.REQUEST);
        requestMsg.addReceiver(ams);
        requestMsg.setOntology(JADEManagementOntology.getInstance().getName());
        requestMsg.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
        requestMsg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        
        try 
        {
            cm.fillContent(requestMsg, a);
            return requestMsg;
        }
        catch(Exception c)
        {
            c.printStackTrace();
        }
        
        return null;
    }
    
    
    private void registerJadeMgrOntologyIfNotExists(ContentManager cm)
    {
        String jMgrName = JADEManagementOntology.getInstance().getName();
        
        for(String ontologyName : cm.getOntologyNames())
        {
            if(ontologyName.equalsIgnoreCase(jMgrName))
                break;
        }
        
        cm.registerOntology(JADEManagementOntology.getInstance());
    }
    
    
    private void registerFipaSlLangIfNotExists(ContentManager cm)
    {
        for(String langName : cm.getLanguageNames())
        {
            if(langName.equalsIgnoreCase(FIPANames.ContentLanguage.FIPA_SL))
                break;
        }
        
        Codec codec = new SLCodec();
        cm.registerLanguage(codec, FIPANames.ContentLanguage.FIPA_SL);
    }
}
