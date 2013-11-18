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
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;

public class AircraftAgent extends Agent {
    
    private int aircraftID;
    private int capacity;
    private int speed;
    private double travelledDistance;
    private Coord2D departureAirportLocation, arrivalAirportLocation;
    private boolean aircraftAvailable; // Is the aircraft in use by another route
    private boolean aircraftFunctional; // Is the aircraft functional
    private AID[] infoListeners;
    
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
            
            System.out.println("Aircraft " + getAID().getLocalName() + " has ID " + aircraftID);
            System.out.println("Aircraft " + getAID().getLocalName() + " has capacity " + capacity);
            System.out.println("Aircraft " + getAID().getLocalName() + " has speed " + speed);
            
            registerToDF();
            addBehaviour(new BestAircraftRequestsServerBehaviour()); // Serve the reschedule request (Cyclic)
            addBehaviour(new BestAircraftOrderServerBehaviour()); // Serve the reschedule order (Cyclic)
            addBehaviour(new RequestInfoListenerBehaviour()); // Request and subscribe listeners for aircraft info (Oneshot)
            addBehaviour(new AirportLocationRequestBehaviour()); // Request arrival airport location (Behaviour)
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
            Coord2D currentLocation = LinearCoordCalculator.INSTANCE.getCoordinates(departureAirportLocation, arrivalAirportLocation, travelledDistance);
            
            ACLMessage info = new ACLMessage(ACLMessage.INFORM);
            info.setConversationId(aircraftStartConID);
            info.setContent(currentLocation.X + "," + currentLocation.Y + "," + arrivalAirportLocation.X + "," + arrivalAirportLocation.Y + "," + speed);
            
            for (int i = 0; i < infoListeners.length; i++) {
                info.addReceiver(infoListeners[i]);
            }
            
            myAgent.send(info);
        }
    }
    
    private class RequestInfoListenerBehaviour extends OneShotBehaviour {
        
        @Override
        public void action() {
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setName(nameOfGUIAgent); // Get departure airport AID
            template.addServices(sd);
            
            try {
                DFAgentDescription[] results = DFService.search(myAgent, template);
                for (int i = 0; i < results.length; i++) {
                    infoListeners[i] = results[i].getName();
                }
                System.out.println("GUI added as listener of aircraft info");
            } catch (FIPAException ex) {
                ex.printStackTrace();
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
                        
                        int seperator = location.indexOf(',');
                        departureAirportLocation.X = Integer.parseInt(location.substring(0, seperator));
                        departureAirportLocation.Y = Integer.parseInt(location.substring(seperator + 1));
                        seperator = location.indexOf(',', seperator);
                        arrivalAirportLocation.X = Integer.parseInt(location.substring(0, seperator));
                        seperator = location.indexOf(',', seperator);
                        arrivalAirportLocation.Y = Integer.parseInt(location.substring(seperator + 1));
                        System.out.println("Coordinates for departure and arrival airport got for aircraft " + myAgent.getLocalName());
                        
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
