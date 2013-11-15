package Agents;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.Hashtable;
import static Utils.Settings.*;

public class AirportAgent extends Agent {

    int airportID;
    int coordinateX;
    int coordinateY;
    Hashtable<AID, String> aircrafts;
    
    @Override
    protected void setup() {  
        System.out.println("Airport-agent " + getAID().getName() + " is ready");
        
        // Get the identifier of the cross and add behaviours
        Object[] args = getArguments();
        if ( args!=null && args.length>0 ) {
            airportID = (Integer) args[0];
            coordinateX = (Integer) args[1];
            coordinateY = (Integer) args[2];
             
            registerToDF(); 
            
            addBehaviour(new LocationRequestsServerBehaviour());
        } else {
            System.out.println("No arguments specified specified");
            doDelete();
        }
    }

    private void registerToDF() {
        // Register the airport service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(typeOfAirportAgent);
        sd.setName(nameOfAirportAgent+airportID);
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

        System.out.println("Airport agent " + getAID().getName() + " terminating");
    }
     
    
    /**
     * Serves the location request
     */
    private class LocationRequestsServerBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(airportLocationConID), MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {

                ACLMessage reply = msg.createReply();
                
                String response = coordinateX + "," + coordinateY;

                reply.setConversationId(airportLocationConID);
                reply.setPerformative(ACLMessage.INFORM);
                reply.setContent(response);

                myAgent.send(reply);
            } else {
                block();
            }
        }
    }
}
