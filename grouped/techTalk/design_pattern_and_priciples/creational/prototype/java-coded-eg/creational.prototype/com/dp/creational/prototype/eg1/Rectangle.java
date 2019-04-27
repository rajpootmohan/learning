package com.dp.creational.prototype.eg1;

public class Rectangle extends Shape
{
    private int width;

    private int height;

    public Rectangle()
    {
    }

    public Rectangle(Rectangle rectangle)
    {
        super(rectangle);
        if (rectangle != null) {
            this.width = rectangle.width;
            this.height = rectangle.height;
        }
    }

    @Override
    public Shape clone()
    {
        return new Rectangle(this);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Rectangle) || !(super.equals(obj)))
            return false;
        Rectangle rec = (Rectangle) obj;
        return this.width == rec.width && this.height == rec.height;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }
}
