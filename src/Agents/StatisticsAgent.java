package Agents;

import static Utils.Settings.*;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StatisticsAgent extends Agent {

    File overbookedSeatsFile = new File("OverbookedSeats.txt");
    File routeTimeFile = new File("RouteTime.txt");
    File costFile = new File("FlightCost.txt");

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
                List<String> items = Arrays.asList(content.split(","));
                String overbookedSeats = items.get(0);
                String routeTimeSeconds = items.get(1);
                String overallCost = items.get(2);

                try {
                    FileWriter fileWriter = new FileWriter(overbookedSeatsFile, true);
                    fileWriter.write(overbookedSeats+",");
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("Could not write to file "+overbookedSeatsFile.getName());
                }
                
                try {
                    FileWriter fileWriter = new FileWriter(routeTimeFile, true);
                    fileWriter.write(routeTimeSeconds+",");
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("Could not write to file "+routeTimeFile.getName());
                }
                
                try {
                    FileWriter fileWriter = new FileWriter(costFile, true);
                    fileWriter.write(overallCost+",");
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("Could not write to file "+costFile.getName());
                }
            } else {
                block();
            }
        }
    }
}
