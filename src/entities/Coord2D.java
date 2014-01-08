package entities;

/**
 * Represents a 2D coordinate with an X and Y value.
 * This class is supposed to be as light weight as possible,
 * like 'structs' as we know it from C#.
 * Hence the lack of mutators and accessors.
 */
public class Coord2D 
{
    public double X, Y;
    public Coord2D() {}
    
    /**
     * Constructs a 2D coordinate from the given X and Y value.
     * @param x The initial value of X.
     * @param y The initial value of Y.
     */
    public Coord2D(double x, double y)
    {
        this.X = x;
        this.Y = y;
    }
    
    /**
     * Checks whether this coordinate is identical to the other coordinate.
     * @param other The other coordinate on which to perform the equality check.
     * @return true if this coordinate is equal to the other coordinate.
     */
    public boolean equals(Coord2D other)
    {
        return X == other.X && Y == other.Y;
    }
    
    /**
     * Validates this coordinate.
     * @return true if both X and Y are positive.
     */
    public boolean isValid()
    {
        return X >= 0 && Y >= 0;
    }
    
    @Override
    public String toString()
    {
        return "[" + X + ", " + Y + "]";
    }
}
