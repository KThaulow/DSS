
import entities.Airport;
import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import entities.agentargs.*;
import java.util.ArrayList;
import java.util.Date;
import mediator.AircraftManager;
import mediator.AirportManager;

public class MainAgent extends Agent
{
    
    
    @Override
    protected void setup() 
    {
        System.out.println("Successfully initialized main agent.");
        
        String containerName;
        
        try
        {
            containerName = getContainerController().getContainerName();
        }
        catch(Exception c)
        {
            containerName = "Main-Container";
        }

        setupAllAgents();
    }
    
    /***
     * Creates and sets up all the agents
     */
    private void setupAllAgents()
    {   
        ArrayList<IAgentArgs> airportAgentArgs = createAirportAgentsArgs();
        ArrayList<IAgentArgs> aircraftAgentArgs = createAircraftAgentsArgs();
        ArrayList<IAgentArgs> routeAgentArgs = createRouteAgentArgs();
        
        for(int i = 0; i < airportAgentArgs.size(); i++)
            createAgent("apAgent"+i, "Agents.AirportAgent", airportAgentArgs.get(i));
        
        for(int i = 0; i < aircraftAgentArgs.size(); i++)
            createAgent("acAgent"+i, "Agents.AircraftAgent", aircraftAgentArgs.get(i));
        
        for(int i = 0; i < routeAgentArgs.size(); i++)
            createAgent("rAgent"+i, "Agents.RouteAgent", routeAgentArgs.get(i));
        
        createAgent("GUIAgent", "Agents.GUIAgent", null);
        
        createAgent("StatisticsAgent", "Agents.StatisticsAgent", null);
    }
    
    private ArrayList<IAgentArgs> createAircraftAgentsArgs()
    {
        ArrayList<IAgentArgs> acAgentArgs = new ArrayList<>();
        acAgentArgs.add(new AircraftAgentArgs(AircraftManager.getInstance().getAircraft("KaspersFly"), AirportManager.getInstance().getAirprot("EKCH")));
        acAgentArgs.add(new AircraftAgentArgs(AircraftManager.getInstance().getAircraft("PetersFly"), AirportManager.getInstance().getAirprot("ENGM")));
        acAgentArgs.add(new AircraftAgentArgs(AircraftManager.getInstance().getAircraft("KristiansFly"), AirportManager.getInstance().getAirprot("EDDF")));
        
        return acAgentArgs;
    }
    
    private ArrayList<IAgentArgs> createAirportAgentsArgs()
    {
        ArrayList<IAgentArgs> airportAgentArgs = new ArrayList<>();
        airportAgentArgs.add(new AirportAgentArgs(AirportManager.getInstance().getAirprot("EKCH")));
        airportAgentArgs.add(new AirportAgentArgs(AirportManager.getInstance().getAirprot("ENGM")));
        airportAgentArgs.add(new AirportAgentArgs(AirportManager.getInstance().getAirprot("EDDF")));
        
        return airportAgentArgs;
    }
    
    private ArrayList<IAgentArgs> createRouteAgentArgs()
    {
        long now = new Date().getTime();
        ArrayList<IAgentArgs> routeAgentArgs = new ArrayList<>();
        
        Airport depAirport = AirportManager.getInstance().getAirprot("EKCH");
        Airport arrAirport = AirportManager.getInstance().getAirprot("EDDF");
        Date earliestArrival = new Date(now + 3600 * 1000);
        Date latest = new Date(now + 3600 * 4 * 1000);
        routeAgentArgs.add(new RouteAgentArgs(0, 150, depAirport, arrAirport, earliestArrival, latest));
       
        return routeAgentArgs;
    }
    
    /**
     * Creates an agent safely
     * @param agentName Name of the agent
     * @param className Class name of the agent
     * @param args Arguments of the agent
     */
    private void createAgent(String agentName, String className, IAgentArgs args)
    {
        try {
            setUpAgent(agentName, className, args);
        }
        catch (StaleProxyException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Sets up an agent
     * @param agentName Name of the agent
     * @param className Class name of the agent
     * @param args Arguments for the agent
     * @throws StaleProxyException 
     */
    private void setUpAgent(String agentName, String className, IAgentArgs args) throws StaleProxyException 
    {
        ((AgentController)getContainerController().createNewAgent(agentName, className, args != null ? args.asObjectArray() : null)).start();
    }
    
    @Override
    protected void takeDown() 
    {
        System.out.println("Shutting down main");
    }
}
