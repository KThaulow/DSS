package Agents;

import GUI.GUIInterface;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import static Utils.Settings.*;

public class GUIAgent extends Agent {

    GUIInterface guiInterface; // The gui interface
    
    protected void setup() {
        guiInterface = new GUIInterface();
        registerToDF();
        System.out.println("Setup gui agent");
        //addBehaviour(new SomeBehaviour());
//        addBehaviour(new RequestGui(this, 500));
    }
    
    private void registerToDF() {
        // Register the airport service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(typeOfGUIAgent);
        sd.setName(nameOfGUIAgent);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
    
    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        System.out.println("Airport agent " + getAID().getName() + " terminating");
    }
    
    private class RequestAirports extends OneShotBehaviour {

        @Override
        public void action() {
//            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(locationID), MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

            
        }

        
    } 
    
    
}
