package Agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import static Utils.Settings.*;

public class AircraftAgent extends Agent {
    
 
    
    int aircraftID;
    int coordinateX;
    int coordinateY;
    int capacity;
    int speed;
    boolean aircraftAvailable; // Is the aircraft in use by another route
    boolean aircraftFunctional; // Is the aircraft functional

    @Override
    protected void setup() {
        System.out.println("Aircraft-agent " + getAID().getName() + " is ready");

        // Get the ID of the route as a startup argument
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            aircraftID = (Integer) args[0];
            capacity = (Integer) args[1];
            speed = (Integer) args[2];
            
            System.out.println("Aircraft " + getAID().getLocalName() + " has ID " + aircraftID);
            System.out.println("Aircraft " + getAID().getLocalName() + " has capacity " + capacity);
            System.out.println("Aircraft " + getAID().getLocalName() + " has speed " + speed);
            
            registerToDF();
            
            addBehaviour(new BestAircraftRequestsServerBehaviour()); // Serve the reschedule request

            addBehaviour(new BestAircraftOrderServerBehaviour()); // Serve the reschedule order
        } else {
            System.out.println("No arguments specified specified");
            doDelete();
        }
        
    }
    
    private void registerToDF() {
        // Register the plane service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(typeOfAircraftAgent);
        sd.setName(nameOfAircraftAgent + aircraftID);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
    
    @Override
    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        
        System.out.println("Plane agent " + getAID().getName() + " terminating");
    }

    /**
     * Serves the reschedule request from the RouteAgent
     */
    private class BestAircraftRequestsServerBehaviour extends CyclicBehaviour {
        
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(bestAircraftID), MessageTemplate.MatchPerformative(ACLMessage.CFP));
            
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it
                String departureAirport = msg.getContent();
                ACLMessage reply = msg.createReply();
                
                String response = "0";

                /**
                 * response = ... Calculate cost Get capacity of aircraft
                 */
                reply.setPerformative(ACLMessage.PROPOSE);
                reply.setContent(response);
                
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }

    /**
     * Serves the reschedule order from the RouteAgent
     */
    private class BestAircraftOrderServerBehaviour extends CyclicBehaviour {
        
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(bestAircraftID), MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL));
            
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it
                ACLMessage reply = msg.createReply();
                
                if (aircraftFunctional) {
                    reply.setPerformative(ACLMessage.INFORM);
                    System.out.println("Aircraft " + myAgent.getName() + " has been assigned to route " + msg.getSender());
                } else {
                    reply.setPerformative(ACLMessage.CANCEL);
                    System.out.println("Aircraft " + myAgent.getLocalName() + " is not functional");
                }
                
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }
}
