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
import static Utils.Settings.*;
import jade.core.behaviours.Behaviour;
/**
 *
 * @author Fuglsang
 */
public class GUIAgent extends Agent {
    
    private enum AirportsCoordinatesSteps {

        REQUEST_AIRPORT_COORDINATES, GET_COORDINATES_FROM_AIRPORTS, DONE;
    }
    
    GUIInterface guiInterface; // The gui interface
    
    protected void setup() {
        guiInterface = new GUIInterface();
        registerToDF();
        System.out.println("Setup gui agent");
        //addBehaviour(new SomeBehaviour());
//        addBehaviour(new RequestGui(this, 500));
        addBehaviour(new RequestAirports());
    }
    
    private void registerToDF() {
        // Register the airport service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(typeOfGUIAgent);
        sd.setName(nameOfGUIAgent);
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
    
    private class RequestAirports extends Behaviour {        
        private MessageTemplate mt; // The template to receive replies
        private int repliesCnt = 0; // The counter of replies from airport agents        
        private AirportsCoordinatesSteps step = AirportsCoordinatesSteps.REQUEST_AIRPORT_COORDINATES;

        @Override
        public void action() {  
            int numberOfAirports = 0;
            AID[] airports;
            switch(step) {
                case REQUEST_AIRPORT_COORDINATES:                                 
                    // Template for getting all aircraft agents
                    DFAgentDescription template = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType(typeOfAirportAgent); // Get all airports
                    template.addServices(sd);
                    try {
                        DFAgentDescription[] results = DFService.search(myAgent, template);
                        numberOfAirports = results.length; 
                        airports = new AID[results.length];
                        for (int i = 0; i < results.length; ++i) {
                            airports[i] = results[i].getName();
                        }

                        // Sent message to airport
                        ACLMessage cfp = new ACLMessage(ACLMessage.REQUEST);                        
                        for (int i = 0; i < airports.length; ++i) {
                            System.out.println("Send message to all airports");
                            cfp.addReceiver(airports[i]);
                        }
                        cfp.setConversationId(airportLocationConID);
                        myAgent.send(cfp);
                        // Prepare the template to get proposals
                        mt = MessageTemplate.and(MessageTemplate.MatchConversationId(airportLocationConID), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
                        step = AirportsCoordinatesSteps.GET_COORDINATES_FROM_AIRPORTS;
                        System.out.println("CFP for all the airports coordinates");
                    } catch (FIPAException fe) {
                        fe.printStackTrace();
                    }
                    break; 
                    
                case GET_COORDINATES_FROM_AIRPORTS: 
                    System.out.println("GET_COORDINATES_FROM_AIRPORTS");
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {                        
                        // Reply received
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            // this is an offer                            
                            System.out.println("Coordinates received from " + reply.getSender().getName());
                            System.out.println("The coordinates are " + reply.getContent()); 
                            String[] coordinates = reply.getContent().split(",");
                            System.out.println("coordinates " + coordinates[0] + " " + coordinates[1]);
                            guiInterface.drawAirport(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[0]), reply.getSender().getName());
                            repliesCnt++;
                        }
                        
                        if (repliesCnt == numberOfAirports) {
                            // We received all replies  
                            step = AirportsCoordinatesSteps.DONE; 
                        } else {
                            step = AirportsCoordinatesSteps.GET_COORDINATES_FROM_AIRPORTS; 
                        }
                    } else {
                        System.out.println("We are done");
                        block();
                    }
                    break;
            }
        }

        @Override
        public boolean done() {
            return (step == AirportsCoordinatesSteps.DONE); 
        }

        
    } 
    
    
}
