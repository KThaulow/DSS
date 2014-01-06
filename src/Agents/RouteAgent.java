package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.List;
import static Utils.Settings.*;
import entities.Airport;
import entities.agentargs.*;
import jade.core.behaviours.CyclicBehaviour;
import java.util.Date;

public class RouteAgent extends Agent {

    private Date earliestArrivalTime;
    private Date latestArrivalTime;

    private enum BestAircraft {

        REQUEST_AIRCRAFT_COST, GET_PROPOSAL_FROM_ALL_AIRCRAFTS, ORDER_AIRCRAFT, GET_RECEIPT, IDLE;
    }

    private enum AirportLocation {

        REQUEST_AIRPORT_LOCATION, GET_LOCATION, DONE;
    }

    private int routeID;
    private AID aircraft;
    private int soldTickets;
    private Airport departureAirport, arrivalAirport;
    private int routeDistance;

    @Override
    protected void setup() {
        System.out.println("Route-agent " + getAID().getName() + " is ready");

        // Get the ID of the route as a startup argument
        RouteAgentArgs args = RouteAgentArgs.createAgentArgs(getArguments());

        if (args != null) {
            routeID = args.getRouteID();
            departureAirport = args.getDepartureAirport();
            arrivalAirport = args.getArrivalAirport();
            soldTickets = args.getNumOfPassengers();
            earliestArrivalTime = args.getEarliestArrivalTime();
            latestArrivalTime = args.getLatestArrivalTime();

            registerToDF();
            addBehaviour(new StartRouteBehaviour());
            //addBehaviour(new RequestBestAircraft());

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
        sd.setType(TYPE_OF_ROUTE_AGENT);
        sd.setName(NAME_OF_ROUTE_AGENT);
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
     * Listens for start requests
     */
    private class StartRouteBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(START_ROUTE_CON_ID), MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
 
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                System.out.println("Start request best aicraft");
               addBehaviour(new RequestBestAircraft());
            } else {
                block();
            }
        }
    }

    /**
     * This is the complex behaviour used to request aircraft agents to be
     * assigned to fly this route
     */
    private class RequestBestAircraft extends Behaviour {

        private AID bestPlane; // The agent who provides the best offer
        private double lowestCost; // The lowest cost
        private int repliesCnt = 0; // The counter of replies from plane agents
        private MessageTemplate mt; // The template to receive replies
        private BestAircraft step = BestAircraft.REQUEST_AIRCRAFT_COST;
        private List<AID> unavailableAircrafts = new ArrayList<>();
        private int numberOfAircrafts = 0;
        private long startTime, totalTime;

        private static final String REMOTE_DF = "df@192.168.1.41:1099/JADE"; // "df@192.168.1.45:1099/JADE";
        private static final String REMOTE_ADDRESS = "http://Kristian-Yoga:7778/acc"; //"http://Kristian-Laptop:7778/acc";
        
        @Override
        public void action() {
            AID[] aircrafts;

            switch (step) {
                case REQUEST_AIRCRAFT_COST: // Send the CFP (Call For Proposal) to all aircrafts

                    startTime = System.currentTimeMillis();
                    
                    // Template for getting all aircraft agents
                    DFAgentDescription template = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType(TYPE_OF_AIRCRAFT_AGENT); // Get all aircrafts
                    template.addServices(sd);
                    try {
                        AID remoteDF = new AID();
                        remoteDF.setName(REMOTE_DF);
                        remoteDF.addAddresses(REMOTE_ADDRESS);
                        DFAgentDescription[] results = DFService.search(myAgent, remoteDF, template);
                        aircrafts = new AID[results.length];
                        for (int i = 0; i < results.length; ++i) {
                            aircrafts[i] = results[i].getName();
                            System.out.println("Aircraft "+results[i].getName()+" found");
                        }

                        numberOfAircrafts = aircrafts.length;

                        // Sent message to airport
                        ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                        for (int i = 0; i < aircrafts.length; ++i) {
                            cfp.addReceiver(aircrafts[i]);
                        }
                        cfp.setContent(departureAirport.getIcao() + "," + arrivalAirport.getIcao() + "," + soldTickets); // Send the departure airport and sold tickets
                        cfp.setConversationId(BEST_AIRCRAFT_CON_ID);
                        myAgent.send(cfp);
                        // Prepare the template to get proposals
                        MessageTemplate performatives = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE), MessageTemplate.MatchPerformative(ACLMessage.REFUSE));
                        mt = MessageTemplate.and(MessageTemplate.MatchConversationId(BEST_AIRCRAFT_CON_ID), performatives);
                        step = BestAircraft.GET_PROPOSAL_FROM_ALL_AIRCRAFTS;

                        System.out.println("CFP for best fitted aircraft send to all aircrafts");
                    } catch (FIPAException fe) {
                        fe.printStackTrace();
                    }
                    break;

                case GET_PROPOSAL_FROM_ALL_AIRCRAFTS:  // Receive all proposals from the planes in the current airport
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        // Reply received
                        if (reply.getPerformative() == ACLMessage.PROPOSE) {
                            // this is an offer
                            double cost = Double.parseDouble(reply.getContent());
                            if (bestPlane == null || cost < lowestCost) { // If aircraft is not available the cost is -1
                                // This is the best offer at present
                                lowestCost = cost;
                                bestPlane = reply.getSender();
                            }
                            System.out.println("Proposals received from " + reply.getSender().getName() + "(" + reply.getSender().getAddressesArray()[0] + ") with cost " + cost);
                        }
                        repliesCnt++;
                        if (repliesCnt >= numberOfAircrafts) {
                            // We received all replies
                            totalTime = System.currentTimeMillis() - startTime;
                            System.out.println("Total time (ms) for route "+myAgent.getLocalName() + ": " + totalTime);
                            step = BestAircraft.ORDER_AIRCRAFT;
                        }

                    } else {
                        block();
                    }
                    break;

                case ORDER_AIRCRAFT: // Send the reschedule order to the plane that provided the best offer 
                    ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    order.addReceiver(bestPlane);
                    order.setConversationId(BEST_AIRCRAFT_CON_ID);
                    order.setContent(departureAirport.getIcao() + "," + arrivalAirport.getIcao() + "," + soldTickets); // Send the departure airport and sold tickets  
                    myAgent.send(order);
                    // Prepare the template to get the order reply
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId(BEST_AIRCRAFT_CON_ID), MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                    step = BestAircraft.GET_RECEIPT;
                    System.out.println("Send reschedule order to plane " + bestPlane.getName());
                    break;

                case GET_RECEIPT: // Receive the best aircraft order reply
                    reply = myAgent.receive(mt);
                    if (reply != null) {
                        System.out.println("Receipt reply received");
                        // Reschedule order reply received
                        if (reply.getPerformative() == ACLMessage.CONFIRM) {
                            aircraft = bestPlane;
                            System.out.println(reply.getSender().getName() + " succesfully rescheduled.\nCost = " + lowestCost);
                            step = BestAircraft.IDLE;

                        } else if (reply.getPerformative() == ACLMessage.REFUSE) {
                            bestPlane = null;
                            
                            step = BestAircraft.REQUEST_AIRCRAFT_COST;
                        }

                    } else {
                        block();
                    }
                    break;
            }
        }

        @Override
        public boolean done() {
            if (step == BestAircraft.ORDER_AIRCRAFT && bestPlane == null) {
                System.out.println("No aircraft was found for route " + myAgent.getLocalName());
            }
            return ((step == BestAircraft.ORDER_AIRCRAFT && bestPlane == null) || step == BestAircraft.IDLE);
        }
    }
}
