/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import GUI.GUIInterface;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
/**
 *
 * @author Fuglsang
 */
public class GUIAgent extends Agent {
    private static final String typeOfAgent = "GUI";
    private static final String nameOfAgent = "GUIAgent";
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
        sd.setType(typeOfAgent);
        sd.setName(nameOfAgent);
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
    
    private class RequestGui extends TickerBehaviour {

        public RequestGui(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            System.out.println("Update Gui");            
        }
        
    } 
}
