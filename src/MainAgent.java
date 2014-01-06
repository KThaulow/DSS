
import Utils.Settings;
import entities.Aircraft;
import entities.Airport;
import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import entities.agentargs.*;
import jade.core.behaviours.TickerBehaviour;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mediator.AircraftManager;
import mediator.AirportManager;
import mediator.CsvFile;
import mediator.CsvFileRepository;

public class MainAgent extends Agent {

    @Override
    protected void setup() {
        System.out.println("Successfully initialized main agent.");

        String containerName;

        try {
            containerName = getContainerController().getContainerName();
        } catch (Exception c) {
            containerName = "Main-Container";
        }

        setupAllAgents();
    }

    /**
     * *
     * Creates and sets up all the agents
     */
    private void setupAllAgents() {
        ArrayList<IAgentArgs> airportAgentArgs = createAirportAgentsArgs();
        ArrayList<IAgentArgs> aircraftAgentArgs = createAircraftAgentsArgs();
        ArrayList<IAgentArgs> routeAgentArgs = createRouteAgentArgs();

        for (int i = 0; i < airportAgentArgs.size(); i++) {
            createAgent("apAgent" + i, "Agents.AirportAgent", airportAgentArgs.get(i));
        }

        for (int i = 0; i < aircraftAgentArgs.size(); i++) {
            createAgent("acAgent" + i, "Agents.AircraftAgent", aircraftAgentArgs.get(i));
        }

        /*for (int i = 0; i < routeAgentArgs.size(); i++) {
            createAgent("rAgent" + i, "Agents.RouteAgent", routeAgentArgs.get(i));
        }*/

        createAgent("GUIAgent", "Agents.GUIAgent", null);

        createAgent("StatisticsAgent", "Agents.StatisticsAgent", null);

        addBehaviour(new RouteGeneratorBehaviour(this, Settings.ROUTE_GENERATOR_MS_DELAY));
    }

    private ArrayList<IAgentArgs> createAircraftAgentsArgs() {
        ArrayList<IAgentArgs> acAgentArgs = new ArrayList<>();
        acAgentArgs.add(new AircraftAgentArgs(AircraftManager.getInstance().getAircraft("KaspersFly"), AirportManager.getInstance().getAirport("EKCH")));
        acAgentArgs.add(new AircraftAgentArgs(AircraftManager.getInstance().getAircraft("PetersFly"), AirportManager.getInstance().getAirport("ENGM")));
        acAgentArgs.add(new AircraftAgentArgs(AircraftManager.getInstance().getAircraft("KristiansFly"), AirportManager.getInstance().getAirport("EDDF")));
        acAgentArgs.add(new AircraftAgentArgs(AircraftManager.getInstance().getAircraft("HenriksFly"), AirportManager.getInstance().getAirport("LFPG")));
        acAgentArgs.add(new AircraftAgentArgs(AircraftManager.getInstance().getAircraft("KaspersFly1"), AirportManager.getInstance().getAirport("EDDM")));
        acAgentArgs.add(new AircraftAgentArgs(AircraftManager.getInstance().getAircraft("PetersFly1"), AirportManager.getInstance().getAirport("LFPG")));
        acAgentArgs.add(new AircraftAgentArgs(AircraftManager.getInstance().getAircraft("KristiansFly1"), AirportManager.getInstance().getAirport("EHRD")));
        acAgentArgs.add(new AircraftAgentArgs(AircraftManager.getInstance().getAircraft("HenriksFly1"), AirportManager.getInstance().getAirport("LTBA")));
        acAgentArgs.add(new AircraftAgentArgs(AircraftManager.getInstance().getAircraft("KaspersFly2"), AirportManager.getInstance().getAirport("UUDD")));
        acAgentArgs.add(new AircraftAgentArgs(AircraftManager.getInstance().getAircraft("PetersFly2"), AirportManager.getInstance().getAirport("LIRA")));

        
        /*for(Aircraft aircraft : AircraftManager.getInstance().getAllAircrafts().values())
        {
            acAgentArgs.add(new AircraftAgentArgs(aircraft, AirportManager.getInstance().getAirport("EKCH")));
        }*/
        
        
        return acAgentArgs;
    }

    private ArrayList<IAgentArgs> createAirportAgentsArgs() {
        ArrayList<IAgentArgs> airportAgentArgs = new ArrayList<>();
        airportAgentArgs.add(new AirportAgentArgs(AirportManager.getInstance().getAirport("EKCH")));
        airportAgentArgs.add(new AirportAgentArgs(AirportManager.getInstance().getAirport("ENGM")));
        airportAgentArgs.add(new AirportAgentArgs(AirportManager.getInstance().getAirport("EDDF")));
        airportAgentArgs.add(new AirportAgentArgs(AirportManager.getInstance().getAirport("EGLC")));
        airportAgentArgs.add(new AirportAgentArgs(AirportManager.getInstance().getAirport("EDDM")));
        airportAgentArgs.add(new AirportAgentArgs(AirportManager.getInstance().getAirport("LFPG")));
        airportAgentArgs.add(new AirportAgentArgs(AirportManager.getInstance().getAirport("EHRD")));
        airportAgentArgs.add(new AirportAgentArgs(AirportManager.getInstance().getAirport("LTBA")));
        airportAgentArgs.add(new AirportAgentArgs(AirportManager.getInstance().getAirport("UUDD")));
        airportAgentArgs.add(new AirportAgentArgs(AirportManager.getInstance().getAirport("LIRA")));

        return airportAgentArgs;
    }

    private ArrayList<IAgentArgs> createRouteAgentArgs() {
        long now = new Date().getTime();
        ArrayList<IAgentArgs> routeAgentArgs = new ArrayList<>();

        Airport depAirport = AirportManager.getInstance().getAirport("EKCH");
        Airport arrAirport = AirportManager.getInstance().getAirport("EDDF");
        Date earliestArrival = new Date(now + 3600 * 1000);
        Date latest = new Date(now + 3600 * 4 * 1000);
        routeAgentArgs.add(new RouteAgentArgs(0, 170, depAirport, arrAirport, earliestArrival, latest));

        Airport depAirport1 = AirportManager.getInstance().getAirport("EDDF");
        Airport arrAirport1 = AirportManager.getInstance().getAirport("ENGM");
        Date earliestArrival1 = new Date(now + 3600 * 1000);
        Date latest1 = new Date(now + 3600 * 4 * 1000);
        routeAgentArgs.add(new RouteAgentArgs(1, 150, depAirport1, arrAirport1, earliestArrival1, latest1));

        return routeAgentArgs;
    }

    /**
     * Generates a new route for every tick
     */
    private class RouteGeneratorBehaviour extends TickerBehaviour {

        private int routeIncrementer = 0;
        private int routeID = 2;
        Airport depAirport;
        Airport arrAirport;
        Date earliestArrival;
        Date latest;
        long now;

        public RouteGeneratorBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            now = new Date().getTime();
            earliestArrival = new Date(now + 3600 * 1000);
            latest = new Date(now + 3600 * 4 * 1000);
            int bookedSeats = 0;
            switch (routeIncrementer) {
                case 0:
                    depAirport = AirportManager.getInstance().getAirport("EKCH");
                    arrAirport = AirportManager.getInstance().getAirport("ENGM");
                    bookedSeats = 100;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 1:
                    depAirport = AirportManager.getInstance().getAirport("EDDF");
                    arrAirport = AirportManager.getInstance().getAirport("EGLC");
                    bookedSeats = 120;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 2:
                    depAirport = AirportManager.getInstance().getAirport("EDDM");
                    arrAirport = AirportManager.getInstance().getAirport("LFPG");
                    bookedSeats = 150;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 3:
                    depAirport = AirportManager.getInstance().getAirport("EHRD");
                    arrAirport = AirportManager.getInstance().getAirport("LTBA");
                    bookedSeats = 265;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 4:
                    depAirport = AirportManager.getInstance().getAirport("UUDD");
                    arrAirport = AirportManager.getInstance().getAirport("LIRA");
                    bookedSeats = 90;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 5:
                    depAirport = AirportManager.getInstance().getAirport("ENGM");
                    arrAirport = AirportManager.getInstance().getAirport("EDDF");
                    bookedSeats = 160;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 6:
                    depAirport = AirportManager.getInstance().getAirport("EGLC");
                    arrAirport = AirportManager.getInstance().getAirport("EDDM");
                    bookedSeats = 80;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 7:
                    depAirport = AirportManager.getInstance().getAirport("LFPG");
                    arrAirport = AirportManager.getInstance().getAirport("EHRD");
                    bookedSeats = 150;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 8:
                    depAirport = AirportManager.getInstance().getAirport("LTBA");
                    arrAirport = AirportManager.getInstance().getAirport("UUDD");
                    bookedSeats = 110;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 9:
                    depAirport = AirportManager.getInstance().getAirport("LIRA");
                    arrAirport = AirportManager.getInstance().getAirport("EKCH");
                    bookedSeats = 50;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 10:
                    depAirport = AirportManager.getInstance().getAirport("EKCH");
                    arrAirport = AirportManager.getInstance().getAirport("EDDF");
                    bookedSeats = 265;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 11:
                    depAirport = AirportManager.getInstance().getAirport("ENGM");
                    arrAirport = AirportManager.getInstance().getAirport("EGLC");
                    bookedSeats = 135;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 12:
                    depAirport = AirportManager.getInstance().getAirport("EDDM");
                    arrAirport = AirportManager.getInstance().getAirport("EHRD");
                    bookedSeats = 160;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 13:
                    depAirport = AirportManager.getInstance().getAirport("LFPG");
                    arrAirport = AirportManager.getInstance().getAirport("LTBA");
                    bookedSeats = 100;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 14:
                    depAirport = AirportManager.getInstance().getAirport("LIRA");
                    arrAirport = AirportManager.getInstance().getAirport("UUDD");
                    bookedSeats = 140;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 15:
                    depAirport = AirportManager.getInstance().getAirport("LTBA");
                    arrAirport = AirportManager.getInstance().getAirport("LIRA");
                    bookedSeats = 150;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 16:
                    depAirport = AirportManager.getInstance().getAirport("EHRD");
                    arrAirport = AirportManager.getInstance().getAirport("LFPG");
                    bookedSeats = 150;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 17:
                    depAirport = AirportManager.getInstance().getAirport("UUDD");
                    arrAirport = AirportManager.getInstance().getAirport("EDDM");
                    bookedSeats = 60;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 18:
                    depAirport = AirportManager.getInstance().getAirport("EGLC");
                    arrAirport = AirportManager.getInstance().getAirport("EKCH");
                    bookedSeats = 120;
                    routeID++;
                    routeIncrementer++;
                    break;
                case 19:
                    depAirport = AirportManager.getInstance().getAirport("EDDF");
                    arrAirport = AirportManager.getInstance().getAirport("ENGM");
                    bookedSeats = 155;
                    routeID++;
                    routeIncrementer++;
                    break;
            }
            createAgent("rAgent" + routeID, "Agents.RouteAgent", new RouteAgentArgs(routeID, bookedSeats, depAirport, arrAirport, earliestArrival, latest));
        }

    }

    /**
     * Creates an agent safely
     *
     * @param agentName Name of the agent
     * @param className Class name of the agent
     * @param args Arguments of the agent
     */
    private void createAgent(String agentName, String className, IAgentArgs args) {
        try {
            setUpAgent(agentName, className, args);
        } catch (StaleProxyException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sets up an agent
     *
     * @param agentName Name of the agent
     * @param className Class name of the agent
     * @param args Arguments for the agent
     * @throws StaleProxyException
     */
    private void setUpAgent(String agentName, String className, IAgentArgs args) throws StaleProxyException {
        ((AgentController) getContainerController().createNewAgent(agentName, className, args != null ? args.asObjectArray() : null)).start();
    }

    @Override
    protected void takeDown() {
        System.out.println("Shutting down main");

        try {
            CsvFileRepository.INSTANCE.getCsvFile("AllAircraftStats").write();
            CsvFileRepository.INSTANCE.getCsvFile("ChosenAircraftStats").write();
        } catch (IOException ex) {
            Logger.getLogger(MainAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
