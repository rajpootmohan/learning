package com.dp.structural.adapter.eg;

/**
 * SquarePegs are not compatible with RoundHoles (they were implemented by previous development team). But we have to
 * integrate them into our program.
 */
public class SquarePeg
{
    private double width;

    public SquarePeg(double width)
    {
        this.width = width;
    }

    public double getWidth()
    {
        return width;
    }
}
