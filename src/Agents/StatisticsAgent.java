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
import mediator.CsvFileRepository;

public class StatisticsAgent extends Agent {

    private CsvFile allAircraftsFile;
    private CsvFile chosenAircraftsFile;

    @Override
    protected void setup() {
        System.out.println("Statistics-agent " + getAID().getName() + " is ready");

        registerToDF();

        addBehaviour(new StatisticsInformServerBehaviour());
        
        allAircraftsFile = new CsvFile("AllAircraftStats.csv");
        chosenAircraftsFile = new CsvFile("ChosenAircraftStats.csv");
        
        CsvFileRepository.INSTANCE.register(allAircraftsFile);
        CsvFileRepository.INSTANCE.register(chosenAircraftsFile);
    }

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
     * Adds the received record to the statistics repository
     */
    private class StatisticsInformServerBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(STATISTICS_CON_ID), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
            ACLMessage reply = myAgent.receive(mt);
            if (reply != null) {
                String content = reply.getContent();
                Stats s = new Stats(content);
                if (s.getRouteTime().isEmpty()) {
                    allAircraftsFile.addStats(s);
                } else {
                    chosenAircraftsFile.addStats(s);
                }

            } else {
                block();
            }
        }
    }
}
