package Agents;

import Utils.LinearCoordCalculator;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import static Utils.Settings.*;
import entities.Aircraft;
import entities.Airport;
import entities.Coord2D;
import entities.agentargs.*;
import entities.cost.*;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AircraftAgent extends Agent {

    private Airport currentAirport;     
    private double travelledDistance;
    private Coord2D departureAirportLocation, arrivalAirportLocation, currentLocation;
    private boolean aircraftAvailable; // Is the aircraft in use by another route
    private boolean aircraftFunctional; // Is the aircraft functional
    private List<AID> infoListeners;
    private Aircraft aircraft;
    
    private enum ArrivalAirport {

        REQUEST_AIRPORT_LOCATION, GET_AIRPORT_LOCATION, DONE;
    }

    @Override
    protected void setup() {
        System.out.println("Aircraft-agent " + getAID().getName() + " is ready");

        // Get the ID of the route as a startup argument
        AircraftAgentArgs acAgentArgs = AircraftAgentArgs.createAgentArgs(getArguments());

        if (acAgentArgs != null) {

            aircraft = acAgentArgs.getAircraft();
            currentAirport = acAgentArgs.getAirport();
            currentLocation = currentAirport.getLocation();
            aircraftFunctional = true;
            
            infoListeners = new ArrayList<>();

            registerToDF();

            addBehaviour(new BestAircraftRequestsServerBehaviour()); 
            addBehaviour(new BestAircraftOrderServerBehaviour()); 
            addBehaviour(new InfoListenerRequestServerBehaviour()); 
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
        sd.setType(TYPE_OF_AIRCRAFT_AGENT);
        sd.setName(NAME_OF_AIRCRAFT_AGENT + aircraft.getTailnumber());
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
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(BEST_AIRCRAFT_CON_ID), MessageTemplate.MatchPerformative(ACLMessage.CFP));

            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                System.out.println("Best aircraft request served");
                
                // Message received. Process it
                String content = msg.getContent();
                List<String> items = Arrays.asList(content.split(","));
                double departureX = Double.parseDouble(items.get(0));
                double departureY = Double.parseDouble(items.get(1));
                double arrivalX = Double.parseDouble(items.get(2));
                double arrivalY = Double.parseDouble(items.get(3));
                int soldTickets = Integer.parseInt(items.get(4));
                departureAirportLocation = new Coord2D(departureX, departureY);
                arrivalAirportLocation = new Coord2D(arrivalX, arrivalY);
                ACLMessage reply = msg.createReply();

                ICostModel cost = new SimpleCostModel(soldTickets, aircraft.getCapacity(), currentLocation, departureAirportLocation, arrivalAirportLocation, aircraft.getSpeed(), aircraft.getFuelBurnRate());

                String response = cost.calculateCost() + "";

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
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(BEST_AIRCRAFT_CON_ID), MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL));

            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it
                ACLMessage reply = msg.createReply();
                
                if (aircraftFunctional) {
                    reply.setPerformative(ACLMessage.INFORM);
                    System.out.println("Aircraft " + myAgent.getName() + " has been assigned to route " + msg.getSender() + " and started");
                    travelledDistance = 0; // Reset traveled distance

                    addBehaviour(new AircraftStartInformBehaviour(myAgent, AIRCRAFT_START_TIMER_MS)); // Start flight
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

    /**
     * Update aircraft location and inform about location, destination and speed
     */
    private class AircraftStartInformBehaviour extends TickerBehaviour {

        public AircraftStartInformBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {

            travelledDistance += aircraft.getSpeed() / (AIRCRAFT_START_TIMER_MS * MS_TO_HOUR);
            currentLocation = LinearCoordCalculator.INSTANCE.getCoordinates(departureAirportLocation, arrivalAirportLocation, travelledDistance);

            ACLMessage info = new ACLMessage(ACLMessage.INFORM);
            info.setConversationId(AIRCRAFT_START_CON_ID);
            info.setContent(currentLocation.X + "," + currentLocation.Y + "," + arrivalAirportLocation.X + "," + arrivalAirportLocation.Y + "," + aircraft.getSpeed());

            for (AID infoListener : infoListeners) {
                System.out.println("INFO sent to " + infoListener.getLocalName() + " with current location " + currentLocation.toString() + " and arrival " + arrivalAirportLocation.toString());
                info.addReceiver(infoListener);
            }

            myAgent.send(info);
        }
    }

    /**
     * Request listener(s) for aircraft info
     */
    private class InfoListenerRequestServerBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(AIRCRAFT_SUBSCRIPTION_CON_ID), MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
            ACLMessage reply = myAgent.receive(mt);
            if (reply != null) {
                infoListeners.add(reply.getSender());
                System.out.println("Listener added " + reply.getSender() + " for aircraft agent " + myAgent.getLocalName());
            } else {
                block();
            }
        }

    }
}
