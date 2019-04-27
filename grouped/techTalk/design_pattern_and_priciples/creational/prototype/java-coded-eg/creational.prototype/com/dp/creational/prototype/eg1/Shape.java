package com.dp.creational.prototype.eg1;

public abstract class Shape
{
    private int X;

    private int Y;

    private String color;

    public Shape()
    {
    }

    public Shape(Shape target)
    {
        if (target != null) {
            this.X = target.X;
            this.Y = target.Y;
            this.color = target.color;
        }
    }

    public abstract Shape clone();

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Shape))
            return false;
        Shape shape2 = (Shape) obj;
        return this.X == shape2.X && this.Y == shape2.Y && this.color == shape2.color;
    }

    public int getX()
    {
        return X;
    }

    public void setX(int x)
    {
        X = x;
    }

    public int getY()
    {
        return Y;
    }

    public void setY(int y)
    {
        Y = y;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }
}
