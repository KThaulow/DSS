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
import entities.Airport;
import entities.agentargs.*;
import java.util.ArrayList;
import java.util.List;

public class AirportAgent extends Agent {

    private Airport airport;

    List<String> aircrafts;

    @Override
    protected void setup() {
        System.out.println("Airport-agent " + getAID().getName() + " is ready");

        // Get the identifier of the cross and add behaviours
        AirportAgentArgs args = AirportAgentArgs.createAgentArgs(getArguments());

        if (args != null) {
            airport = args.getAirport();
            registerToDF();
            aircrafts = new ArrayList<>();
            addBehaviour(new AircraftPresenceInformServerBehaviour());
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
        sd.setType(TYPE_OF_AIRPORT_AGENT);
        sd.setName(NAME_OF_AIRPORT_AGENT + airport.getId());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    /**
     * Gets messages from aircrafts of arrival and departure in this airport
     */
    private class AircraftPresenceInformServerBehaviour extends CyclicBehaviour {

        public void action() {
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(AIRCRAFT_PRESENCE_CON_ID), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
            ACLMessage reply = myAgent.receive(mt);
            if (reply != null) {
                String presence = reply.getContent();

                if (presence.equals("arrival")) {
                    System.out.println("Aircraft " + reply.getSender().getLocalName() + " has arrived at " + airport.getName());
                    aircrafts.add(reply.getSender().getLocalName());
                } else if (presence.equals("departure")) {
                    System.out.println("Aircraft " + reply.getSender().getLocalName() + " has departed from " + airport.getName());
                    if (aircrafts.contains(reply.getSender().getLocalName())) {
                        aircrafts.remove(reply.getSender().getLocalName());
                    }
                }
            } else {
                block();
            }

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

}
