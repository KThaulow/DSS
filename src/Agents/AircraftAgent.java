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
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mediator.AirportManager;

public class AircraftAgent extends Agent {

    private Airport currentAirport, departureAirport, arrivalAirport;
    private double travelledDistanceRoute, travelledDistanceLeg;
    private Coord2D departureAirportLocation, arrivalAirportLocation, currentLocation;
    private boolean aircraftAvailable; // Is the aircraft in use by another route
    private boolean aircraftFunctional; // Is the aircraft functional
    private List<AID> infoListeners;
    private Aircraft aircraft;
    private int overbookedSeats;
    private String cost;
    private double routeTimeSeconds;

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
                String departureICAO = items.get(0);
                String arrivalICAO = items.get(1);
                int soldTickets = Integer.parseInt(items.get(2));
                departureAirport = AirportManager.getInstance().getAirprot(departureICAO);
                arrivalAirport = AirportManager.getInstance().getAirprot(arrivalICAO);
                departureAirportLocation = departureAirport.getLocation();
                arrivalAirportLocation = arrivalAirport.getLocation();

                overbookedSeats = soldTickets - aircraft.getCapacity();

                ACLMessage reply = msg.createReply();
                ICostModel costModel = new SimpleCostModel(soldTickets, aircraft.getCapacity(), currentLocation, departureAirportLocation, arrivalAirportLocation, aircraft.getSpeed(), aircraft.getFuelBurnRate());
                cost = costModel.calculateCost() + "";
                reply.setPerformative(ACLMessage.PROPOSE);
                reply.setContent(cost);

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
                    travelledDistanceRoute = 0; // Reset travelled distance for route
                    travelledDistanceLeg = 0; // Reset travelled distance for leg
                    routeTimeSeconds = 0; // Reset route minutes

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

        private Coord2D otherLocation;
        private double distanceSinceLastUpdate;
        private boolean IsArrivedAtDeparture = false;

        public AircraftStartInformBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            distanceSinceLastUpdate = aircraft.getSpeed() / (MS_TO_HOUR / AIRCRAFT_START_TIMER_MS);
            travelledDistanceLeg += distanceSinceLastUpdate;
            travelledDistanceRoute = distanceSinceLastUpdate;
            routeTimeSeconds += AIRCRAFT_START_TIMER_MS / MS_TO_SECONDS;

            ACLMessage info = new ACLMessage(ACLMessage.INFORM);
            info.setConversationId(AIRCRAFT_START_CON_ID);

            // Aircraft is not at the departure airport
            if (currentAirport.equals(departureAirport) == false) {
                if (otherLocation == null) {
                    otherLocation = currentLocation;
                    System.out.println("Aircraft " + myAgent.getLocalName() + " is not at the departure airport " + departureAirport.getName() + " but at location " + otherLocation.toString());
                }
                currentLocation = LinearCoordCalculator.INSTANCE.getCoordinates(otherLocation, departureAirportLocation, travelledDistanceLeg);

                if (currentLocation.equals(departureAirportLocation)) { // Aircraft has arrived at the departure airport
                    currentAirport = departureAirport;
                    travelledDistanceLeg = 0;
                    System.out.println("Aircraft " + myAgent.getLocalName() + " with current location " + currentLocation.toString() + " has arrived at " + departureAirport.getName() + " airport");
                }
                info.setContent(currentLocation.X + "," + currentLocation.Y + "," + departureAirportLocation.X + "," + departureAirportLocation.Y + "," + aircraft.getSpeed());
                System.out.println("Aircraft " + myAgent.getLocalName() + " has current location " + currentLocation.toString() + " and departure " + departureAirportLocation.toString());
            }
            // Aircraft is at the departure airport
            if (currentAirport.equals(departureAirport)) {
                currentLocation = LinearCoordCalculator.INSTANCE.getCoordinates(departureAirportLocation, arrivalAirportLocation, travelledDistanceLeg);
                info.setContent(currentLocation.X + "," + currentLocation.Y + "," + arrivalAirportLocation.X + "," + arrivalAirportLocation.Y + "," + aircraft.getSpeed());
                System.out.println("Aircraft " + myAgent.getLocalName() + " has current location " + currentLocation.toString() + " and arrival " + arrivalAirportLocation.toString());
            }

            for (AID infoListener : infoListeners) {
                info.addReceiver(infoListener);
            }

            if (currentLocation.equals(arrivalAirportLocation)) {
                System.out.println("Aircraft " + myAgent.getLocalName() + " with current location " + currentLocation.toString() + " has arrived at " + arrivalAirport.getName() + " airport");
                currentAirport = arrivalAirport;

                addBehaviour(new InformStatisticsBehaviour()); // Send information to statistics agent

                stop();
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

    /**
     * Informs the statistics agent with aircraft info
     */
    private class InformStatisticsBehaviour extends OneShotBehaviour {

        private AID statisticsAgent;

        @Override
        public void action() {
            System.out.println("Inform statistics agent");
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType(TYPE_OF_STATISTICS_AGENT); // Get all aircrafts
            template.addServices(sd);
            try {
                DFAgentDescription[] results = DFService.search(myAgent, template);
                statisticsAgent = results[0].getName();
                System.out.println("Info sent to " + statisticsAgent.getLocalName());
                ACLMessage info = new ACLMessage(ACLMessage.INFORM);
                info.addReceiver(statisticsAgent);
                info.setContent(overbookedSeats + "," + routeTimeSeconds + "," + cost);
                info.setConversationId(STATISTICS_CON_ID);
                myAgent.send(info);
            } catch (FIPAException fe) {
                fe.printStackTrace();
            }
        }
    }
}
