
package Utils;

/**
 * Settings for agents, messages, behaviours etc.
 */
public class Settings {
    public static final String TYPE_OF_AIRCRAFT_AGENT = "aircraft";
    public static final String NAME_OF_AIRCRAFT_AGENT = "aircraftAgent";
    public static final int AIRCRAFT_START_TIMER_MS = 100;
    
    public static final String TYPE_OF_ROUTE_AGENT = "route";
    public static final String NAME_OF_ROUTE_AGENT = "routeAgent";
    
    public static final String TYPE_OF_AIRPORT_AGENT = "airport";
    public static final String NAME_OF_AIRPORT_AGENT = "airportAgent";
    
    public static final String TYPE_OF_GUI_AGENT = "GUI";
    public static final String NAME_OF_GUI_AGENT = "GUIAgent";
    
    public static final String TYPE_OF_STATISTICS_AGENT = "Statistics";
    public static final String NAME_OF_STATISTICS_AGENT = "StatisticsAgent";
    
    public static final String AIRPORT_LOCATION_CON_ID = "airportLocation";
    public static final String BEST_AIRCRAFT_CON_ID = "bestAircraft";
    public static final String AIRCRAFT_START_CON_ID = "aircraftInfo";
    public static final String AIRCRAFT_SUBSCRIPTION_CON_ID = "aircraftSubscription";
    public static final String STATISTICS_CON_ID = "statisticsInfo";
    public static final String AIRCRAFT_PRESENCE_CON_ID = "aircraftPresence";
    public static final String START_ROUTE_CON_ID = "startRoute";
    
    public static final int NUMBER_OF_AIRCRAFT_AGENTS = 10;
    public static final int NUMBER_OF_AIRPORT_AGENTS = 10;
    public static final int NUMBER_OF_ROUTE_AGENTS = 20;    
    
    public static final int MS_TO_HOUR = 3600000;
    public static final int MS_TO_SECONDS = 1000;
    
    public static final int TIME_FACTOR = 600;
    
    public static final int ROUTE_GENERATOR_MS_DELAY = 5000;
}
