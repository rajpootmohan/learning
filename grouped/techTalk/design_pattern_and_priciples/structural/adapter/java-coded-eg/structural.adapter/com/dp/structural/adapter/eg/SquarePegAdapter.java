package com.dp.structural.adapter.eg;

public class SquarePegAdapter extends RoundPeg
{
    private SquarePeg squarePeg;

    public SquarePegAdapter(SquarePeg squarePeg)
    {
        this.squarePeg = squarePeg;
    }

    @Override
    public double getRadius()
    {
        // Calculate a minimum circle radius, which can fit this peg.
        double halfWidth = squarePeg.getWidth() / 2;
        double squareHalfWidth = Math.pow(halfWidth, 2);
        return Math.sqrt(squareHalfWidth + squareHalfWidth);
    }
}
