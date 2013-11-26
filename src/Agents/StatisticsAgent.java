package Agents;

import static Utils.Settings.*;
import entities.Stats;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mediator.CsvFile;

public class StatisticsAgent extends Agent {

    File overbookedSeatsFile = new File("OverbookedSeats.csv");
    File routeTimeFile = new File("RouteTime.csv");
    File costFile = new File("FlightCost.csv");

    @Override
    protected void setup() {
        System.out.println("Statistics-agent " + getAID().getName() + " is ready");

        registerToDF();
        
        addBehaviour(new InfoListenerRequestServerBehaviour());
        
    }

    // Free seats
    // Off loaded passengers
    // Time
    // Overall cost
    // Comma seperated
    private void registerToDF() {
        // Register the plane service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(TYPE_OF_STATISTICS_AGENT);
        sd.setName(NAME_OF_STATISTICS_AGENT);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    /**
     * Request listener(s) for aircraft info
     */
    private class InfoListenerRequestServerBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(STATISTICS_CON_ID), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
            ACLMessage reply = myAgent.receive(mt);
            if (reply != null) {
                String content = reply.getContent();
                Stats s = new Stats(content);
                
                CsvFile.INSTANCE.addStats(s.getId(), s);
                
                try {
                    CsvFile.INSTANCE.write();
                } catch (IOException ex) {
                    Logger.getLogger(StatisticsAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                block();
            }
        }
    }
}
