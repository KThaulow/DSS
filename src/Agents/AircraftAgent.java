package Agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import static Utils.Settings.*;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;

public class AircraftAgent extends Agent {

    int aircraftID;
    int coordinateX;
    int coordinateY;
    int capacity;
    int speed;
    int arrivalAirportX, arrivalAirportY;
    boolean aircraftAvailable; // Is the aircraft in use by another route
    boolean aircraftFunctional; // Is the aircraft functional

    private enum ArrivalAirport {

        REQUEST_ARRIVAL_AIRPORT, GET_ARRIVAL_AIRPORT, DONE;
    }

    @Override
    protected void setup() {
        System.out.println("Aircraft-agent " + getAID().getName() + " is ready");

        // Get the ID of the route as a startup argument
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            aircraftID = (Integer) args[0];
            capacity = (Integer) args[1];
            speed = (Integer) args[2];

            System.out.println("Aircraft " + getAID().getLocalName() + " has ID " + aircraftID);
            System.out.println("Aircraft " + getAID().getLocalName() + " has capacity " + capacity);
            System.out.println("Aircraft " + getAID().getLocalName() + " has speed " + speed);

            registerToDF();

            addBehaviour(new BestAircraftRequestsServerBehaviour()); // Serve the reschedule request

            addBehaviour(new BestAircraftOrderServerBehaviour()); // Serve the reschedule order
            
            addBehaviour(new ArrivalAirportRequestBehaviour()); // Request arrival airport location

            addBehaviour(new AircraftDataInformBehaviour(this, aircraftInfoTimerMs)); // Informs listeners about the aircrafts data (location, speed, destination)
            
            

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
                String departureAirport = msg.getContent();
                ACLMessage reply = msg.createReply();

                String response = "0";

                /**
                 * response = ... Calculate cost Get capacity of aircraft
                 */
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
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(bestAircraftConID), MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL));

            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it
                ACLMessage reply = msg.createReply();

                if (aircraftFunctional) {
                    reply.setPerformative(ACLMessage.INFORM);
                    System.out.println("Aircraft " + myAgent.getName() + " has been assigned to route " + msg.getSender());
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
     * Informs about location, destination and speed
     */
    private class AircraftDataInformBehaviour extends TickerBehaviour {

        public AircraftDataInformBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            
            ACLMessage info = new ACLMessage(ACLMessage.INFORM);
            info.setConversationId(aircraftInfoConID);
            info.setContent(coordinateX + "," + coordinateY + "," + arrivalAirportX + "," + arrivalAirportY + "," + speed);
            myAgent.send(info);
        }
    }

    /**
     * Request arrival airport location
     */
    private class ArrivalAirportRequestBehaviour extends Behaviour {

        private MessageTemplate mt; // The template to receive replies
        private ArrivalAirport step = ArrivalAirport.REQUEST_ARRIVAL_AIRPORT;

        @Override
        public void action() {
            switch (step) {
                case REQUEST_ARRIVAL_AIRPORT:

                    ACLMessage order = new ACLMessage(ACLMessage.REQUEST);
                    order.setConversationId(airportLocationConID);
                    order.setContent(myAgent.getName());
                    myAgent.send(order);
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId(arrivalAirportConID), MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                    step = ArrivalAirport.GET_ARRIVAL_AIRPORT;
                    break;

                case GET_ARRIVAL_AIRPORT:
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        // Reschedule order reply received
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            // Purchase succesful. We can terminate.
                            String location = reply.getContent();

                            int seperator = location.indexOf(',');

                            arrivalAirportX = Integer.parseInt(location.substring(0, seperator));
                            arrivalAirportY = Integer.parseInt(location.substring(seperator + 1));
                            System.out.println("Coordinates for arrival airport got for aircraft " + myAgent.getLocalName());

                            step = ArrivalAirport.DONE;
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
            return step == ArrivalAirport.DONE;
        }
    }
}
