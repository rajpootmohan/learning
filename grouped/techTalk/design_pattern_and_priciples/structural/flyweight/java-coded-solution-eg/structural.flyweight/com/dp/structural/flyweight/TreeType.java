package com.dp.structural.flyweight;

import java.awt.Color;
import java.awt.Graphics;

// Contains state shared by several trees
//This Flyweight class contains a portion of a state of a
//tree. These field store values that hardly unique for
//each particular tree. For instance, you will not find
//here the tree coordinates. But the texture and colors
//shared between many trees are here. Since this data is
//usually BIG, you would waste a lot of memory by keeping
//it in the each tree object. Instead, we can extract
//texture, color and other repeating data into a separate
//object, which can be referenced from lots of individual
//tree objects.
public class TreeType
{
    private String name;

    private Color color;

    private String otherTreeData;

    public TreeType(String name, Color color, String otherTreeData)
    {
        super();
        this.name = name;
        this.color = color;
        this.otherTreeData = otherTreeData;
    }

    public void draw(Graphics g, int x, int y)
    {
        g.setColor(Color.BLACK);
        g.fillRect(x - 1, y, 3, 5);
        g.setColor(color);
        g.fillOval(x - 5, y - 10, 10, 10);
    }
}
