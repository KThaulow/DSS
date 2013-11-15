/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.cost;

/**
 * Defines a cost model for the calculation of costs, when querying for the most optimal aircraft.
 * @author pla
 */
public interface ICostModel 
{
    /**
     * Calculates and returns a cost of using this specific aircraft.
     * @return The calculated cost.
     */
    public double calculateCost();
}
