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

public class RouteAgent extends Agent {

    private static final String typeOfAgent = "route";
    private static final String nameOfAgent = "routeAgent";
    private static final String conversationID = "reschedule";
    private static final String agentToFind = "airport";

    private enum Reschedule {

        REQUEST_PLANES, GET_PROPOSAL_FROM_PLANES, ORDER_PLANE, GET_RECEIPT, IDLE;
    }

    private int routeID;
    private int soldTickets;
    private AID departureAirport;
    private AID arrivalAirport;
    private AID aircraft;

    protected void setup() {
        System.out.println("Route-agent " + getAID().getName() + " is ready");

        // Get the ID of the route as a startup argument
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            routeID = (Integer) args[0];
            System.out.println("Route has ID " + routeID);

            registerToDF();

            addBehaviour(new RequestReschedule());
        } else {
            System.out.println("No route ID specified");
            doDelete();
        }
    }

    private void registerToDF() {
        // Register the route service in the yellow pages
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
     * This is the complex behaviour used by route agents to request plane
     * agents available in the current airport from the airport agents
     */
    private class RequestReschedule extends Behaviour {

        private AID bestPlane; // The agent who provides the best offer
        private int lowestCost; // The lowest cost
        private int repliesCnt = 0; // The counter of replies from plane agents
        private MessageTemplate mt; // The template to receive replies
        private Reschedule step = Reschedule.REQUEST_PLANES;

        public void action() {
            int planes = 0;
            AID[] aircrafts;
            switch (step) {
                case REQUEST_PLANES: // Send the CFP (Call For Proposal) to all aircrafts

                    // Template for getting all aircraft agent
                    DFAgentDescription template = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("all aircrafts"); // Get all aircrafts
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
                        cfp.setContent("departure airport"); // Send this airport
                        cfp.setConversationId(conversationID);
                        cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Unique value
                        myAgent.send(cfp);
                        // Prepare the template to get proposals
                        mt = MessageTemplate.and(MessageTemplate.MatchConversationId(conversationID), MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                        step = Reschedule.GET_PROPOSAL_FROM_PLANES;

                        System.out.println("CFP send to current airport");
                    } catch (FIPAException fe) {
                        fe.printStackTrace();
                    }
                    break;

                case GET_PROPOSAL_FROM_PLANES:  // Receive all proposals from the planes in the current airport
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
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
                            step = Reschedule.ORDER_PLANE;
                        }

                    } else {
                        System.out.println("No proposals received");
                        block();
                    }
                    break;

                case ORDER_PLANE: // Send the reschedule order to the plane that provided the best offer 
                    ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    order.addReceiver(bestPlane);
                    order.setConversationId(conversationID);
                    order.setReplyWith(conversationID + System.currentTimeMillis());
                    myAgent.send(order);
                    // Prepare the template to get the purchase order reply
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId(conversationID), MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                    step = Reschedule.GET_RECEIPT;
                    System.out.println("Send reschedule order to plane " + bestPlane.getName());
                    break;

                case GET_RECEIPT: // Receive the reschedule order reply
                    reply = myAgent.receive(mt);
                    if (reply != null) {
                        // Reschedule order reply received
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            // Purchase succesful. We can terminate.
                            System.out.println(reply.getSender().getName() + " succesfully rescheduled.\nCost = " + lowestCost);
                            myAgent.doDelete();
                            step = Reschedule.IDLE;
                        }

                    } else {
                        block();
                        System.out.println("No reschedule order reply received");
                    }
                    break;
            }
        }

        public boolean done() {
            return ((step == Reschedule.ORDER_PLANE && bestPlane == null) || step == Reschedule.IDLE);
        }
    }

}
