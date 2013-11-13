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
 * Serves the reschedule request from the RouteAgent
 */
public class RescheduleRequestsServerBehaviour extends CyclicBehaviour {

    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);

        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            // Message received. Process it
            String airport = msg.getContent();
            ACLMessage reply = msg.createReply();

            String response = "";

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
