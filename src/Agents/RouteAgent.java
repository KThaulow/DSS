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

    private enum Reschedule {
        REQUEST_PLANES, GET_NUMBER_OF_PLANES, GET_PROPOSAL_FROM_PLANES, ORDER_PLANE, GET_RECEIPT, IDLE;
    }

    private int routeID;

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
     * This is the behaviour used by route agents to request plane agents
     * available in the current airport from the airport agents
     */
    private class RequestReschedule extends Behaviour {

        private AID bestPlane; // The agent who provides the best offer
        private int lowestCost; // The lowest cost
        private int repliesCnt = 0; // The counter of replies from plane agents
        private MessageTemplate mt; // The template to receive replies
        private Reschedule step = Reschedule.REQUEST_PLANES;

        public void action() {
            int planes = 0;
            AID currentAirport;
            switch (step) {
                case REQUEST_PLANES: // Send the CFP (Call For Proposal) to the current airport

                    // Template for getting the current airport agent
                    DFAgentDescription template = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("this airport"); // Get this airport
                    template.addServices(sd);
                    try {
                        DFAgentDescription[] results = DFService.search(myAgent, template);
                        currentAirport = new AID();
                        currentAirport = results[0].getName();

                        // Sent message to airport
                        ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                        cfp.addReceiver(currentAirport); // this airport
                        cfp.setContent("get all airplanes in this airport"); // Get all airplanes in current airport
                        cfp.setConversationId(conversationID);
                        cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Unique value
                        myAgent.send(cfp);
                        // Prepare the template to get proposals
                        mt = MessageTemplate.and(MessageTemplate.MatchConversationId(conversationID), MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                        step = Reschedule.GET_NUMBER_OF_PLANES;

                        System.out.println("CFP send to current airport");
                    } catch (FIPAException fe) {
                        fe.printStackTrace();
                    }
                    break;

                case GET_NUMBER_OF_PLANES: // Receive number of planes in airport
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        // Reply received
                        if (reply.getPerformative() == ACLMessage.INFORM) {

                            planes = Integer.parseInt(reply.getContent());

                            System.out.println("Planes in current (" + reply.getSender().getName() + ")  airport: " + planes);
                        }
                        step = Reschedule.GET_PROPOSAL_FROM_PLANES;
                    } else {
                        System.out.println("No information received");
                        block();
                    }
                    break;

                case GET_PROPOSAL_FROM_PLANES:  // Receive all proposals from the planes in the current airport
                    reply = myAgent.receive(mt);
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
                    order.setContent("targetRoute"); // TODO: Change to the name of this route???
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
                        }
                        step = Reschedule.IDLE;
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
