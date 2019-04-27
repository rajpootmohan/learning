package com.dp.structural.adapter.eg;

/**
 * RoundHoles are compatible with RoundPegs.
 */
public class RoundHole
{
    private double radius;

    public RoundHole(double radius)
    {
        this.radius = radius;
    }

    public double getRadius()
    {
        return radius;
    }

    public boolean fits(RoundPeg roundPeg)
    {
        if (this.radius >= roundPeg.getRadius())
            return true;
        return false;
    }
}
