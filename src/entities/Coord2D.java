/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

/**
 * Represents a 2D coordinate with an X and Y value.
 * This class is supposed to be as light weight as possible,
 * like 'structs' as we know it from C#.
 * Hence the lack of mutators and accessors.
 * @author pla
 */
public class Coord2D 
{
    public double X, Y;
    public Coord2D() {}
    
    public Coord2D(double x, double y)
    {
        this.X = x;
        this.Y = y;
    }
    
    public boolean equals(Coord2D other)
    {
        return X == other.X && Y == other.Y;
    }
}
