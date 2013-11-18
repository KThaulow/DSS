
import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import entities.agentargs.*;
import java.util.ArrayList;

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
        ArrayList<IAgentArgs> aircraftAgentArgs = createAircraftAgentsArgs();
        ArrayList<IAgentArgs> airportAgentArgs = createAirportAgentsArgs();
        ArrayList<IAgentArgs> routeAgentArgs = createRouteAgentArgs();

        for(int i = 0; i < aircraftAgentArgs.size(); i++)
            createAgent("acAgent"+i, "Agents.AircraftAgent", aircraftAgentArgs.get(i));
        
        for(int i = 0; i < airportAgentArgs.size(); i++)
            createAgent("apAgent"+i, "Agents.AirportAgent", airportAgentArgs.get(i));
        
        for(int i = 0; i < routeAgentArgs.size(); i++)
            createAgent("rAgent"+i, "Agents.RouteAgent", routeAgentArgs.get(i));
        
        createAgent("GUIAgent", "Agents.GUIAgent", null);
    }
    
    private ArrayList<IAgentArgs> createAircraftAgentsArgs()
    {
        ArrayList<IAgentArgs> acAgentArgs = new ArrayList<>();
        acAgentArgs.add(new AircraftAgentArgs(0, 100, 1000, 500));
        acAgentArgs.add(new AircraftAgentArgs(1, 100, 1000, 500));
        acAgentArgs.add(new AircraftAgentArgs(2, 100, 1000, 500));
        
        return acAgentArgs;
    }
    
    private ArrayList<IAgentArgs> createAirportAgentsArgs()
    {
        ArrayList<IAgentArgs> airportAgentArgs = new ArrayList<>();
        airportAgentArgs.add(new AirportAgentArgs(0, 2, 3));
        airportAgentArgs.add(new AirportAgentArgs(1, 5, 6));
        airportAgentArgs.add(new AirportAgentArgs(2, 8, 10));
        
        return airportAgentArgs;
    }
    
    private ArrayList<IAgentArgs> createRouteAgentArgs()
    {
        ArrayList<IAgentArgs> routeAgentArgs = new ArrayList<>();
        routeAgentArgs.add(new RouteAgentArgs(0, 1, 2, 50));
        
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
