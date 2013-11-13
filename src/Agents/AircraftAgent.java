package Agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AircraftAgent extends Agent {

    private static final String typeOfAgent = "aircraft";
    private static final String nameOfAgent = "aircraftAgent";
    
    int aircraftID;
    int capacity;
    double speed;

    protected void setup() {
        registerToDF();
        System.out.println("Hello, this is an aircraft agent!");
        
        addBehaviour(new RescheduleRequestsServer()); // Serve the reschedule request
    }

    private void registerToDF() {
        // Register the plane service in the yellow pages
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

        System.out.println("Plane agent " + getAID().getName() + " terminating");
    }

    /**
     * Serves the reschedule request from the RouteAgent
     */
    private class RescheduleRequestsServer extends CyclicBehaviour {

        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it
                String airport = msg.getContent();
                ACLMessage reply = msg.createReply();

                String response = "";
                
                /**
                 * response = ...
                 * Calculate cost
                 * Get capacity of aircraft
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
    private class RescheduleOrderServer extends CyclicBehaviour {

        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it
                String title = msg.getContent();
                ACLMessage reply = msg.createReply();
                
                reply.setPerformative(ACLMessage.INFORM);
                reply.setContent(response);

                myAgent.send(reply);
            } else {
                block();
            }
        }
    }
}
