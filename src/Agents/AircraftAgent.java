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
import entities.Coord2D;
import entities.agentargs.*;
import entities.cost.SimpleCostModel;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import java.util.Arrays;
import java.util.List;

public class AircraftAgent extends Agent {

    private int aircraftID;
    private int capacity;
    private double speed;
    private double travelledDistance;
    private Coord2D departureAirportLocation, arrivalAirportLocation, currentLocation;
    private boolean aircraftAvailable; // Is the aircraft in use by another route
    private boolean aircraftFunctional; // Is the aircraft functional
    private double fuelBurnRate;
    private List<AID> infoListeners;

    private enum ArrivalAirport {

        REQUEST_AIRPORT_LOCATION, GET_AIRPORT_LOCATION, DONE;
    }

    @Override
    protected void setup() {
        System.out.println("Aircraft-agent " + getAID().getName() + " is ready");

        // Get the ID of the route as a startup argument
        AircraftAgentArgs acAgentArgs = AircraftAgentArgs.createAgentArgs(getArguments());

        if (acAgentArgs != null) {
            aircraftID = acAgentArgs.getAircraftID();
            capacity = acAgentArgs.getCapacity();
            speed = acAgentArgs.getSpeed();
            fuelBurnRate = acAgentArgs.getFuelBurnRate();

            System.out.println("Aircraft " + getAID().getLocalName() + " has ID " + aircraftID);
            System.out.println("Aircraft " + getAID().getLocalName() + " has capacity " + capacity);
            System.out.println("Aircraft " + getAID().getLocalName() + " has speed " + speed);
            System.out.println("Aircraft " + getAID().getLocalName() + " has fuel burn rate " + fuelBurnRate);

            registerToDF();
            addBehaviour(new BestAircraftRequestsServerBehaviour()); // Serve the reschedule request (Cyclic)
            addBehaviour(new BestAircraftOrderServerBehaviour()); // Serve the reschedule order (Cyclic)

            //addBehaviour(new AirportLocationRequestBehaviour()); // Request arrival airport location (Behaviour)
            
            addBehaviour(new InfoListenerRequestServerBehaviour()); // Serves requests for subscriptions for aircraft info (Cyclic)
            
            addBehaviour(new AircraftStartBehaviour(this, 1000));
            //addBehaviour(new AircraftDataInformBehaviour(this, aircraftInfoTimerMs)); // Informs listeners about the aircrafts data (location, speed, destination)
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
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(bestAircraftConID), MessageTemplate.MatchPerformative(ACLMessage.CFP));

            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it
                String content = msg.getContent();
                List<String> items = Arrays.asList(content.split(","));
                int departureX = Integer.parseInt(items.get(0));
                int departureY = Integer.parseInt(items.get(1));
                int arrivalX = Integer.parseInt(items.get(2));
                int arrivalY = Integer.parseInt(items.get(3));
                int soldTickets = Integer.parseInt(items.get(4));
                departureAirportLocation = new Coord2D(departureX, departureY);
                arrivalAirportLocation = new Coord2D(arrivalX, arrivalY);
                ACLMessage reply = msg.createReply();

                //SimpleCostModel cost = new SimpleCostModel(soldTickets, capacity, currentLocation, departureAirpor, travelledDistance, fuelBurnRate);

                //String response = cost + "";

                reply.setPerformative(ACLMessage.PROPOSE);
                //reply.setContent(response);

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
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(bestAircraftConID), MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL));

            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it
                ACLMessage reply = msg.createReply();

                if (aircraftFunctional) {
                    reply.setPerformative(ACLMessage.INFORM);
                    System.out.println("Aircraft " + myAgent.getName() + " has been assigned to route " + msg.getSender() + " and started");
                    travelledDistance = 0; // Reset traveled distance

                    addBehaviour(new AircraftStartBehaviour(myAgent, aircraftStartTimerMs)); // Start flight
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
    private class AircraftStartBehaviour extends TickerBehaviour {

        public AircraftStartBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {

            travelledDistance += speed / (aircraftStartTimerMs * MS_TO_HOUR);
            currentLocation = LinearCoordCalculator.INSTANCE.getCoordinates(departureAirportLocation, arrivalAirportLocation, travelledDistance);

            ACLMessage info = new ACLMessage(ACLMessage.INFORM);
            info.setConversationId(aircraftStartConID);
            info.setContent(currentLocation.X + "," + currentLocation.Y + "," + arrivalAirportLocation.X + "," + arrivalAirportLocation.Y + "," + speed);

            for (AID infoListener : infoListeners) {
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
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(aircraftSubscriptionConID), MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
            ACLMessage reply = myAgent.receive(mt);
            if (reply != null) {
                infoListeners.add(reply.getSender());
            } else {
                block();
                System.out.println("No info listeners for aircraft " + myAgent.getLocalName());
            }
        }

    }

    /**
     * Request departure and arrival airport location
     */
    private class AirportLocationRequestBehaviour extends Behaviour {

        private MessageTemplate mt; // The template to receive replies
        private ArrivalAirport step = ArrivalAirport.REQUEST_AIRPORT_LOCATION;

        @Override
        public void action() {
            switch (step) {
                case REQUEST_AIRPORT_LOCATION:
                    DFAgentDescription template = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setName(nameOfAirportAgent + "aircraftID"); // Get departure airport AID
                    template.addServices(sd);

                    try {
                        DFAgentDescription[] results = DFService.search(myAgent, template);
                        //aircraft = results[0].getName();
                        //System.out.println("Route " + myAgent.getLocalName() + " has aircraft agent " + aircraft.getLocalName());
                    } catch (FIPAException ex) {
                        ex.printStackTrace();
                    }

                    ACLMessage order = new ACLMessage(ACLMessage.REQUEST);
                    order.setConversationId(airportLocationAircraftConID);
                    order.setContent(myAgent.getName());
                    myAgent.send(order);
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId(airportLocationAircraftConID), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
                    step = ArrivalAirport.GET_AIRPORT_LOCATION;
                    break;

                case GET_AIRPORT_LOCATION:
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        // Location received
                        String location = reply.getContent();
                        List<String> items = Arrays.asList(location.split(","));
                        departureAirportLocation.X = Integer.parseInt(items.get(0));
                        departureAirportLocation.Y = Integer.parseInt(items.get(1));
                        arrivalAirportLocation.X = Integer.parseInt(items.get(2));
                        arrivalAirportLocation.Y = Integer.parseInt(items.get(3));

                        System.out.println("Coordinates for departure and arrival airport got for aircraft " + myAgent.getLocalName());
                        System.out.println("Aircraft coordinates: " + departureAirportLocation.toString() + " " + arrivalAirportLocation.toString());

                        step = ArrivalAirport.DONE;

                    } else {
                        block();
                        System.out.println("No airport location reply received for aircraft " + myAgent.getLocalName());
                    }
                    break;
            }

        }

        @Override
        public boolean done() {
            return step == ArrivalAirport.DONE;
        }
    }
}
