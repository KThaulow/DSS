package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.List;
import static Utils.Settings.*;
import jade.core.behaviours.CyclicBehaviour;

public class RouteAgent extends Agent {

    private enum BestAircraft {

        REQUEST_AIRCRAFT, GET_PROPOSAL_FROM_AIRCRAFTS, ORDER_AIRCRAFT, GET_RECEIPT, IDLE;
    }

    private enum AirportLocation {

        REQUEST_AIRPORT_LOCATION, GET_LOCATION, DONE;
    }

    private int routeID;
    private AID departureAirport;
    private AID arrivalAirport;
    private AID aircraft;
    private int soldTickets;
    private int departureAirportID, arrivalAirportID, aircraftID;
    private int departureAirportX, departureAirportY, arrivalAirportX, arrivalAirportY;

    @Override
    protected void setup() {
        System.out.println("Route-agent " + getAID().getName() + " is ready");

        // Get the ID of the route as a startup argument
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            routeID = (Integer) args[0];
            departureAirportID = (Integer) args[1];
            arrivalAirportID = (Integer) args[2];
            aircraftID = (Integer) args[3];
            soldTickets = (Integer) args[4];

            System.out.println("Route " + getAID().getLocalName() + " has ID " + routeID);
            System.out.println("Route " + getAID().getLocalName() + " has departure airport " + departureAirportID);
            System.out.println("Route " + getAID().getLocalName() + " has arrival airport " + arrivalAirportID);
            System.out.println("Route " + getAID().getLocalName() + " has aircraft " + aircraftID);
            System.out.println("Route " + getAID().getLocalName() + " has " + soldTickets + " sold tickets");

            registerToDF();

            addBehaviour(new RequestAssignedAirports()); // Request associated airports

            addBehaviour(new RequestAssignedAircraft()); // Request associated aircraft

            addBehaviour(new RequestBestAircraft()); // Request reschedule of flight
            
            addBehaviour(new ArrivalAirportRequestServerBehaviour()); // Serve arrival airport location request
            
        } else {
            System.out.println("No arguments specified specified");
            doDelete();
        }
    }

    private void registerToDF() {
        // Register the route service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(typeOfRouteAgent);
        sd.setName(nameOfRouteAgent);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    @Override
    public void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        System.out.println("Route agent " + getAID().getName() + " terminating");
    }

    /**
     * One shot behaviour for getting associated airports
     */
    private class RequestAssignedAirports extends OneShotBehaviour {

        @Override
        public void action() {
            // Template for getting all aircraft agent
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setName(nameOfAirportAgent + departureAirportID); // Get departure airport AID
            template.addServices(sd);

            try {
                DFAgentDescription[] results = DFService.search(myAgent, template);
                departureAirport = results[0].getName();
                System.out.println("Route " + myAgent.getLocalName() + " has departure airport agent " + departureAirport.getLocalName());
            } catch (FIPAException ex) {
                ex.printStackTrace();
            }

            template = new DFAgentDescription();
            sd = new ServiceDescription();
            sd.setName(nameOfAirportAgent + arrivalAirportID); // Get arrival airport AID
            template.addServices(sd);

            try {
                DFAgentDescription[] results = DFService.search(myAgent, template);
                arrivalAirport = results[0].getName();
                System.out.println("Route " + myAgent.getLocalName() + " has arrival airport agent " + arrivalAirport.getLocalName());
            } catch (FIPAException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * One shot behaviour for getting associated aircraft
     */
    private class RequestAssignedAircraft extends OneShotBehaviour {

        @Override
        public void action() {
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setName(nameOfAircraftAgent + aircraftID); // Get departure airport AID
            template.addServices(sd);

            try {
                DFAgentDescription[] results = DFService.search(myAgent, template);
                aircraft = results[0].getName();
                System.out.println("Route " + myAgent.getLocalName() + " has aircraft agent " + aircraft.getLocalName());
            } catch (FIPAException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * Behaviour for getting associated airports coordinates
     */
    private class RequestAirportLocationBehaviour extends Behaviour {

        private AirportLocation step = AirportLocation.REQUEST_AIRPORT_LOCATION;
        private MessageTemplate mt; // The template to receive replies
        private int airportCoordinatesReceived = 0;

        @Override
        public void action() {
            switch (step) {
                case REQUEST_AIRPORT_LOCATION:

                    ACLMessage order = new ACLMessage(ACLMessage.REQUEST);
                    order.addReceiver(departureAirport);
                    order.addReceiver(arrivalAirport);
                    order.setConversationId(airportLocationConID);
                    myAgent.send(order);
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId(airportLocationConID), MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                    step = AirportLocation.GET_LOCATION;
                    break;

                case GET_LOCATION:
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        // Reschedule order reply received
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            // Purchase succesful. We can terminate.
                            String location = reply.getContent();
                            int seperator = location.indexOf(',');

                            if (reply.getSender().equals(departureAirport)) {
                                departureAirportX = Integer.parseInt(location.substring(0, seperator));
                                departureAirportY = Integer.parseInt(location.substring(seperator + 1));
                                System.out.println("Coordinates for departure airport " + reply.getSender().getLocalName() + " got for route " + myAgent.getLocalName());
                                airportCoordinatesReceived++;
                            } else if (reply.getSender().equals(departureAirport)) {
                                departureAirportX = Integer.parseInt(location.substring(0, seperator));
                                departureAirportY = Integer.parseInt(location.substring(seperator + 1));
                                System.out.println("Coordinates for arrival airport " + reply.getSender().getLocalName() + " got for route " + myAgent.getLocalName());
                                airportCoordinatesReceived++;
                            }
                            if (airportCoordinatesReceived >= 2) {
                                step = AirportLocation.DONE;
                            }
                        }

                    } else {
                        block();
                        System.out.println("No airport location reply received");
                    }
                    break;
            }
        }

        @Override
        public boolean done() {
            return step == AirportLocation.DONE;
        }

    }

    /**
     * This is the complex behaviour used by route agents to request plane
     * agents to be assigned to fly this route
     */
    private class RequestBestAircraft extends Behaviour {

        private AID bestPlane; // The agent who provides the best offer
        private int lowestCost; // The lowest cost
        private int repliesCnt = 0; // The counter of replies from plane agents
        private MessageTemplate mt; // The template to receive replies
        private BestAircraft step = BestAircraft.REQUEST_AIRCRAFT;
        private List<AID> unavailableAircrafts = new ArrayList<>();

        @Override
        public void action() {
            int planes = 0;
            AID[] aircrafts;

            switch (step) {
                case REQUEST_AIRCRAFT: // Send the CFP (Call For Proposal) to all aircrafts

                    // Template for getting all aircraft agents
                    DFAgentDescription template = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("aircraft"); // Get all aircrafts
                    template.addServices(sd);
                    try {
                        DFAgentDescription[] results = DFService.search(myAgent, template);
                        aircrafts = new AID[results.length];
                        for (int i = 0; i < results.length; ++i) {
                            aircrafts[i] = results[i].getName();
                        }

                        // Sent message to airport
                        ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                        for (int i = 0; i < aircrafts.length; ++i) {
                            cfp.addReceiver(aircrafts[i]);
                        }
                        cfp.setContent(departureAirport.toString()); // Send the departure airport
                        cfp.setConversationId(bestAircraftConID);
                        cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Unique value
                        myAgent.send(cfp);
                        // Prepare the template to get proposals
                        mt = MessageTemplate.and(MessageTemplate.MatchConversationId(bestAircraftConID), MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                        step = BestAircraft.GET_PROPOSAL_FROM_AIRCRAFTS;

                        System.out.println("CFP for best fitted aircraft send to all aircrafts");
                    } catch (FIPAException fe) {
                        fe.printStackTrace();
                    }
                    break;

                case GET_PROPOSAL_FROM_AIRCRAFTS:  // Receive all proposals from the planes in the current airport
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (unavailableAircrafts != null) {
                            if (unavailableAircrafts.contains(reply.getSender())) { // Break if plane is not functional
                                break;
                            }
                        }
                        // Reply received
                        if (reply.getPerformative() == ACLMessage.PROPOSE) {
                            // this is an offer
                            int cost = Integer.parseInt(reply.getContent());
                            if (bestPlane == null || cost < lowestCost) {
                                // This is the best offer at present
                                lowestCost = cost;
                                bestPlane = reply.getSender();
                            }
                            System.out.println("Proposals received from " + reply.getSender().getName() + " with cost " + cost);
                        }
                        repliesCnt++;
                        if (repliesCnt >= planes) {
                            // We received all replies
                            step = BestAircraft.ORDER_AIRCRAFT;
                        }

                    } else {
                        System.out.println("No proposals received");
                        block();
                    }
                    break;

                case ORDER_AIRCRAFT: // Send the reschedule order to the plane that provided the best offer 
                    ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    order.addReceiver(bestPlane);
                    order.setConversationId(bestAircraftConID);
                    order.setReplyWith(bestAircraftConID + System.currentTimeMillis());
                    myAgent.send(order);
                    // Prepare the template to get the order reply
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId(bestAircraftConID), MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                    step = BestAircraft.GET_RECEIPT;
                    System.out.println("Send reschedule order to plane " + bestPlane.getName());
                    break;

                case GET_RECEIPT: // Receive the best aircraft order reply
                    reply = myAgent.receive(mt);
                    if (reply != null) {
                        // Reschedule order reply received
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            // Purchase succesful. We can terminate.
                            System.out.println(reply.getSender().getName() + " succesfully rescheduled.\nCost = " + lowestCost);
                            step = BestAircraft.IDLE;
                        } else if (reply.getPerformative() == ACLMessage.CANCEL) {
                            unavailableAircrafts.add(bestPlane);
                        }

                    } else {
                        block();
                        System.out.println("No reschedule order reply received");
                    }
                    break;
            }
        }

        @Override
        public boolean done() {
            return ((step == BestAircraft.ORDER_AIRCRAFT && bestPlane == null) || step == BestAircraft.IDLE);
        }
    }

    /**
     * Serves request for arrival airport location
     */
    private class ArrivalAirportRequestServerBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(arrivalAirportConID), MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

            ACLMessage reply = myAgent.receive(mt);
            if (reply != null) {

                ACLMessage order = new ACLMessage(ACLMessage.INFORM);
                order.addReceiver(reply.getSender());
                order.setContent(arrivalAirportX + "," + arrivalAirportY);
                myAgent.send(order);
            } else {
                block();
            }
        }

    }

}
