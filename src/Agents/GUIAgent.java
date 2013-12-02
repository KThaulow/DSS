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
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import static Utils.Settings.*;
import gui.GUIMapInterface;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
/**
 *
 * @author Fuglsang
 */
public class GUIAgent extends Agent {
    
    private enum AirportsCoordinatesSteps {

        REQUEST_AIRPORT_COORDINATES, GET_COORDINATES_FROM_AIRPORTS, DONE;
    }
    
    private GUIInterface guiInterface; // The gui interface
    private GUIMapInterface guiMapInterface; 
    private HashMap<String, String> aircratsAgentsMap; 
    
    @Override
    protected void setup() {
//        guiInterface = new GUIInterface();
        guiMapInterface = new GUIMapInterface(); 
        guiMapInterface.drawAirports();
        registerToDF();
        System.out.println("Setup gui agent");
        //addBehaviour(new SomeBehaviour());
//        addBehaviour(new RequestGui(this, 500));
//        addBehaviour(new RequestAirports());        
        System.out.println("Request info listener added");
        addBehaviour(new RequestInfoListenerBehaviour());
        addBehaviour(new GetInfoFromAircraftBehaviour());
        
        aircratsAgentsMap = new HashMap<>(); 
    }
    
    private void registerToDF() {
        // Register the airport service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(TYPE_OF_GUI_AGENT);
        sd.setName(NAME_OF_GUI_AGENT);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
            System.out.println("GUI register to DF");
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

        System.out.println("GUIAgent agent " + getAID().getName() + " terminating");
    }
    
    private class RequestInfoListenerBehaviour extends OneShotBehaviour {
        
        @Override
        public void action() {
            AID[] aircrafts; 
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType(TYPE_OF_AIRCRAFT_AGENT); // Get all airports
            template.addServices(sd);
            try {
                DFAgentDescription[] results = DFService.search(myAgent, template);                 
                aircrafts = new AID[results.length];
                for (int i = 0; i < results.length; ++i) {
                    aircrafts[i] = results[i].getName(); 
                }

                // Sent message to airport
                ACLMessage cfp = new ACLMessage(ACLMessage.REQUEST);                        
                for (int i = 0; i < aircrafts.length; ++i) {
                    System.out.println("Send message to all aircrafts");
                    cfp.addReceiver(aircrafts[i]);
                }
                cfp.setConversationId(AIRCRAFT_SUBSCRIPTION_CON_ID);
                myAgent.send(cfp);

                } catch (FIPAException fe) {
                fe.printStackTrace();
            }
        }
    }
    
    private class GetInfoFromAircraftBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(AIRCRAFT_START_CON_ID), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {                        
                // Reply received
                // this is an offer                            
//                System.out.println("GUI received aircraft name " + msg.getSender().getLocalName() + " and info: " + msg.getContent());
//                List<String> items = Arrays.asList(msg.getContent().split(","));
                aircratsAgentsMap.remove(msg.getSender().getLocalName()); 
                aircratsAgentsMap.put(msg.getSender().getLocalName(), msg.getContent());
                guiMapInterface.drawAircraft(aircratsAgentsMap); 
                //System.out.println("GUI received aircraft name " + msg.getSender().getLocalName() + " and info: " + msg.getContent());
            } else {
                block();
            }
            
            }
    }
    
}
