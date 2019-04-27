package com.dp.creational.prototype.eg1;

public class Circle extends Shape
{
    private int radius;

    public Circle()
    {
    }

    public Circle(Circle target)
    {
        super(target);
        if (target != null) {
            this.radius = target.radius;
        }
    }

    @Override
    public Shape clone()
    {
        return new Circle(this);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Circle) || !(super.equals(obj)))
            return false;
        Circle circle2 = (Circle) obj;
        return this.radius == circle2.radius;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setRadius(int radius)
    {
        this.radius = radius;
    }
}
