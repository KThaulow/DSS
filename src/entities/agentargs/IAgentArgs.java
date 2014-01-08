package entities.agentargs;

/**
 * Defines a common interface for the representation of agent arguments.
 */
public interface IAgentArgs 
{
    /**
     * Converts the agent arguments to an array of objects.
     * @return An array of objects, containing the agent's arguments.
     */
    public Object[] asObjectArray();
}
