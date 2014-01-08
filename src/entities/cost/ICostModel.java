package entities.cost;

/**
 * Defines a cost model for the calculation of costs, when querying for the most optimal aircraft.
 */
public interface ICostModel 
{
    /**
     * Calculates and returns a cost of using this specific aircraft.
     * @return The calculated cost.
     */
    public double calculateCost();
}
