
import Utils.LinearCoordCalculator;
import entities.Coord2D;
import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;


public class MainAgent extends Agent
{
    private static final int NUMBER_OF_AIRCRAFT_AGENTS = 3;
    private static final int NUMBER_OF_AIRPORT_AGENTS = 3;
    private static final int NUMBER_OF_ROUTE_AGENTS = 1;
    
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
    private void setupAllAgents(){
        Integer aircraftArguments[][] = {{0,100,1000},{1,100,1000},{2,100,1000}}; // ID, Capacity, Speed
        for(int i=0; i<NUMBER_OF_AIRCRAFT_AGENTS; i++){
            createAgent("acAgent"+i, "Agents.AircraftAgent", aircraftArguments[i]);
        }
        
        Integer airportArguments[][] = {{0,2,3}, {1,5,6}, {2,8,10}}; // ID, xCoordinate, yCoordinate
        for(int i=0; i<NUMBER_OF_AIRPORT_AGENTS; i++){
            createAgent("apAgent"+i, "Agents.AirportAgent", airportArguments[i]);
        }
        
        Integer routeArguments[][] = {{0,1,2,2,50}}; // ID, departureAirport, arrivalAirport, aircraft, soldTickets
        for(int i=0; i<NUMBER_OF_ROUTE_AGENTS; i++){
            createAgent("rAgent"+i, "Agents.RouteAgent", routeArguments[i]);
        }
        
        createAgent("GUIAgent", "Agents.GUIAgent", null);
    }
    
    /**
     * Creates an agent safely
     * @param agentName Name of the agent
     * @param className Class name of the agent
     * @param args Arguments of the agent
     */
    private void createAgent(String agentName,String className,Object[] args)
    {
        try {
            setUpAgent(agentName,className,args);
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
    private void setUpAgent(String agentName,String className,Object[] args) throws StaleProxyException 
    {
        ((AgentController)getContainerController().createNewAgent(agentName,className,args)).start();
    }
    
    @Override
    protected void takeDown() 
    {
        System.out.println("Shutting down main");
    }
}
