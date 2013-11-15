/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import GUI.GUIInterface;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
/**
 *
 * @author Fuglsang
 */
public class GUIAgent extends Agent {
    private static final String typeOfAgent = "GUI";
    private static final String nameOfAgent = "GUIAgent";
    GUIInterface guiInterface; // The gui interface
    
    protected void setup() {
        guiInterface = new GUIInterface();
        registerToDF();
        System.out.println("Setup gui agent");
        //addBehaviour(new SomeBehaviour());
//        addBehaviour(new RequestGui(this, 500));
    }
    
    private void registerToDF() {
        // Register the airport service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(typeOfAgent);
        sd.setName(nameOfAgent);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
    
    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        System.out.println("Airport agent " + getAID().getName() + " terminating");
    }
    
    private class RequestAirports extends OneShotBehaviour {        
        AID[] airports;

        @Override
        public void action() {            
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Test"), MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
            
            // Template for getting all aircraft agents
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("airport"); // Get all airports
            template.addServices(sd);
            try {
                DFAgentDescription[] results = DFService.search(myAgent, template);
                airports = new AID[results.length];
                for (int i = 0; i < results.length; ++i) {
                    airports[i] = results[i].getName();
                }

                // Sent message to airport
                ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                for (int i = 0; i < airports.length; ++i) {
                    cfp.addReceiver(airports[i]);
                }                
                cfp.setConversationId(bestAircraftID);
                cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Unique value
                myAgent.send(cfp);
                // Prepare the template to get proposals
                mt = MessageTemplate.and(MessageTemplate.MatchConversationId(bestAircraftID), MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                step = RouteAgent.Reschedule.GET_PROPOSAL_FROM_AIRCRAFTS;

                System.out.println("CFP for best fitted aircraft send to all aircrafts");
            } catch (FIPAException fe) {
                fe.printStackTrace();
            }
        }

        
    } 
    
    
}
