/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AgentBehaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Serves the reschedule order from the RouteAgent
 */
public class RescheduleOrderServerBehaviour extends CyclicBehaviour {

    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);

        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            // Message received. Process it
            String arrivalAirport = msg.getContent();
            ACLMessage reply = msg.createReply();

            reply.setPerformative(ACLMessage.INFORM);
            System.out.println("Aircraft " + myAgent.getName() + " has been assigned to route " + msg.getSender());

            myAgent.send(reply);
        } else {
            block();
        }
    }
}
