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
import Utils.SphericalPositionCalculator;
import entities.Aircraft;
import entities.Airport;
import entities.SphericalPosition;
import entities.Stats;
import entities.agentargs.*;
import entities.cost.*;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mediator.AirportManager;
import mediator.CsvFile;

public class AircraftAgent extends Agent {

    private Airport currentAirport, departureAirport, arrivalAirport;
    private double travelledDistanceRoute, travelledDistanceLeg;
    private SphericalPosition departureAirportLocation, arrivalAirportLocation, currentLocation;
    private boolean aircraftAvailable; // Is the aircraft in use by another route
    private boolean aircraftFunctional; // Is the aircraft functional
    private List<AID> infoListeners;
    private Aircraft aircraft;
    private int overbookedSeats;
    private String cost;
    private double routeTimeSeconds;
    private Stats stats;

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
            aircraftAvailable = true;

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
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.PROPOSE);

                String content = msg.getContent();
                List<String> items = Arrays.asList(content.split(","));
                String departureICAO = items.get(0);
                String arrivalICAO = items.get(1);
                int soldTickets = Integer.parseInt(items.get(2));
                Airport departureAirportTemp = AirportManager.getInstance().getAirport(departureICAO);
                Airport arrivalAirportTemp = AirportManager.getInstance().getAirport(arrivalICAO);
                overbookedSeats = soldTickets - aircraft.getCapacity();
                String costTemp = "-1";
                
                if (aircraftAvailable) {
                    // Message received. Process it
                    departureAirport = departureAirportTemp;
                    arrivalAirport = arrivalAirportTemp;
                    departureAirportLocation = departureAirport.getLocation();
                    arrivalAirportLocation = arrivalAirport.getLocation();
                    
                    ICostModel costModel = new SimpleCostModel2(soldTickets, aircraft.getCapacity(), currentLocation, departureAirportLocation, arrivalAirportLocation, aircraft.getSpeed(), aircraft.getFuelBurnRate());
                    cost = costTemp = costModel.calculateCost() + "";
                    reply.setContent(cost);
                } else {
                    reply.setContent(costTemp);
                }

                stats = new Stats("", aircraft.getTailnumber(), departureAirportTemp.getName(), arrivalAirportTemp.getName(), costTemp, currentAirport.getName(), overbookedSeats + "");
                addBehaviour(new InformStatisticsBehaviour()); // Send information to statistics agent
                
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
                    System.out.println("Aircraft " + myAgent.getLocalName() + "(" + aircraft.getTailnumber() + ") has been assigned to route " + msg.getSender().getLocalName() + "(" + departureAirport.getName() + "-" + arrivalAirport.getName() + ") and started");
                    travelledDistanceRoute = 0; // Reset travelled distance for route
                    travelledDistanceLeg = 0; // Reset travelled distance for leg
                    routeTimeSeconds = 0; // Reset route minutes
                    aircraftAvailable = false;
                    addBehaviour(new AircraftStartInformBehaviour(myAgent, AIRCRAFT_START_TIMER_MS)); // Start flight
                } else {
                    reply.setPerformative(ACLMessage.CANCEL);
                    System.out.println("Aircraft " + myAgent.getLocalName() + " is not functional. Requested by " + msg.getSender());
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

        private SphericalPosition otherLocation;
        private double distanceSinceLastUpdate;
        private boolean IsArrivedAtDeparture = false;

        public AircraftStartInformBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            distanceSinceLastUpdate = aircraft.getSpeed() / (MS_TO_HOUR / (AIRCRAFT_START_TIMER_MS * TIME_FACTOR));
            travelledDistanceLeg += distanceSinceLastUpdate;
            travelledDistanceRoute += distanceSinceLastUpdate;
            routeTimeSeconds += AIRCRAFT_START_TIMER_MS / MS_TO_SECONDS;

            ACLMessage info = new ACLMessage(ACLMessage.INFORM);
            info.setConversationId(AIRCRAFT_START_CON_ID);

            // Aircraft is not at the departure airport
            if (currentAirport.equals(departureAirport) == false) {
                if (otherLocation == null) {
                    otherLocation = currentLocation;
                    System.out.println("Aircraft " + myAgent.getLocalName() + " is not at the departure airport " + departureAirport.getName() + " but at location " + otherLocation.toString());
                }
                currentLocation = SphericalPositionCalculator.INSTANCE.getPosition(otherLocation, departureAirportLocation, travelledDistanceLeg);

                if (currentLocation.equals(departureAirportLocation)) { // Aircraft has arrived at the departure airport
                    currentAirport = departureAirport;
                    travelledDistanceLeg = 0;
                    System.out.println("Aircraft " + myAgent.getLocalName() + " with current location " + currentLocation.toString() + " has arrived at " + departureAirport.getName() + " airport");
                }
                info.setContent(currentLocation.getLatitude() + "," + currentLocation.getLongitude() + "," + departureAirportLocation.getLatitude() + "," + departureAirportLocation.getLongitude() + "," + aircraft.getSpeed());
                System.out.println("Aircraft " + myAgent.getLocalName() + " (" + aircraft.getTailnumber() + ") has current location " + currentLocation.toString() + " and departure " + departureAirportLocation.toString() + " (" + departureAirport.getName() + ")");
            }
            // Aircraft is at the departure airport
            if (currentAirport.equals(departureAirport)) {
                currentLocation = SphericalPositionCalculator.INSTANCE.getPosition(departureAirportLocation, arrivalAirportLocation, travelledDistanceLeg);
                info.setContent(currentLocation.getLatitude() + "," + currentLocation.getLongitude() + "," + arrivalAirportLocation.getLatitude() + "," + arrivalAirportLocation.getLongitude() + "," + aircraft.getSpeed());
                System.out.println("Aircraft " + myAgent.getLocalName() + " (" + aircraft.getTailnumber() + ") has current location " + currentLocation.toString() + " and arrival " + arrivalAirportLocation.toString() + " (" + arrivalAirport.getName() + ")" + " and departure " + departureAirport.getName());
            }

            for (AID infoListener : infoListeners) {
                info.addReceiver(infoListener);
            }

            if (currentLocation.equals(arrivalAirportLocation)) {
                System.out.println("Aircraft " + myAgent.getLocalName() + " with current location " + currentLocation.toString() + " has arrived at " + arrivalAirport.getName() + " airport");
                stats = new Stats(routeTimeSeconds+"", aircraft.getTailnumber(), departureAirport.getName(), arrivalAirport.getName(), cost, currentAirport.getName(), overbookedSeats + "");
                addBehaviour(new InformStatisticsBehaviour()); // Send information to statistics agent
                currentAirport = arrivalAirport;
                aircraftAvailable = true;
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
                String statistics = stats.toCsvString();
                info.setContent(statistics);
                info.setConversationId(STATISTICS_CON_ID);
                myAgent.send(info);
            } catch (FIPAException fe) {
                fe.printStackTrace();
            }
        }
    }
}
