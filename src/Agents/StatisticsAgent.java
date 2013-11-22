
package Agents;

import static Utils.Settings.*;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;


public class StatisticsAgent extends Agent{
    
    @Override
    protected void setup() {
        System.out.println("Settings-agent " + getAID().getName() + " is ready");

        registerToDF();

    }
    
    // Free seats
    // Off loaded passengers
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
}
