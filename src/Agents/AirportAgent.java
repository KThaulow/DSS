package Agents;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.Hashtable;

public class AirportAgent extends Agent {

    private static final String typeOfAgent = "airport";
    private static final String nameOfAgent = "airportAgent";
    private static final String locationID = "airportLocation";

    int airportID;
    double coordinateX;
    double coordinateY;
    Hashtable<AID, String> aircrafts;
    
    protected void setup() {
        
        registerToDF();  
        
        // Get the identifier of the cross and add behaviours
        Object[] args = getArguments();
        if ( args!=null && args.length>0 ) {
            coordinateX = (Integer) args[0];
            coordinateY = (Integer) args[0];
             
            
            addBehaviour(new LocationRequestsServerBehaviour());
        }   
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
     
    
    /**
     * Serves the location request
     */
    private class LocationRequestsServerBehaviour extends CyclicBehaviour {

        public void action() {
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(locationID), MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {

                ACLMessage reply = msg.createReply();

                String response = coordinateX + "," + coordinateY;

                reply.setConversationId(locationID);
                reply.setPerformative(ACLMessage.INFORM);
                reply.setContent(response);

                myAgent.send(reply);
            } else {
                block();
            }
        }
    }
}
