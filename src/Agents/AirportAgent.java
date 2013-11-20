package Agents;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.Hashtable;
import static Utils.Settings.*;
import entities.Airport;
import entities.agentargs.*;

public class AirportAgent extends Agent {

  
    private Airport airport;
    
    Hashtable<AID, String> aircrafts;
    
    @Override
    protected void setup() {  
        System.out.println("Airport-agent " + getAID().getName() + " is ready");
        
        // Get the identifier of the cross and add behaviours
        AirportAgentArgs args = AirportAgentArgs.createAgentArgs(getArguments());
        
        if (args != null) {
            airport = args.getAirport();
            registerToDF(); 
        } else {
            System.out.println("No arguments specified specified");
            doDelete();
        }
    }

    private void registerToDF() {
        // Register the airport service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(typeOfAirportAgent);
        sd.setName(nameOfAirportAgent+airport.toString());
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

        System.out.println("Airport agent " + getAID().getName() + " terminating");
    }
    
}
