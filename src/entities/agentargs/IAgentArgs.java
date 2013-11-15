/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.agentargs;

/**
 * Defines a common interface for the representation of agent arguments.
 * @author pla
 */
public interface IAgentArgs 
{
    /**
     * Converts the agent arguments to an array of objects.
     * @return An array of objects, containing the agent's arguments.
     */
    public Object[] asObjectArray();
}
