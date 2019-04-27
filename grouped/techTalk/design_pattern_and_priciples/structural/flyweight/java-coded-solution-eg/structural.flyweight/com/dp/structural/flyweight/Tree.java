package com.dp.structural.flyweight;

import java.awt.Graphics;

//Contains state unique for each tree
//Contextual object contains extrinsic part of tree state.
//Application can create billions of these since they are
//pretty small: just two integer coordinates and one
//reference field.
public class Tree
{
    private int x;

    private int y;

    private TreeType type;

    public Tree(int x, int y, TreeType type)
    {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw(Graphics g)
    {
        type.draw(g, x, y);
    }
}
